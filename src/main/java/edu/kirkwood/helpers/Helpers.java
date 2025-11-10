package edu.kirkwood.helpers;

import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Helpers {
    // From Jason
    /**
     * Checks if a input string is not null or empty
     * @param str a string to be checked to make to make sure its not null or an empty string
     * @return a bool result true if valid string
     */
    public static boolean isValidString(String str) {
        return str != null && !str.equals("");
    }

    /*
     *	Takes a number and rounds that number by the number of
     *	decimal places from the parameter
     *	@Param number The number that will be rounded
     *	@Param number The number of decimal places that the rounded
     *		number will go too.
     *	@Returns The resulted rounded number from the calculation
     */
    public static String round(double number, int numDecPlaces) {
        BigDecimal bigDecimal = new BigDecimal(Double.toString(number));
        bigDecimal = bigDecimal.setScale(numDecPlaces, RoundingMode.HALF_UP).stripTrailingZeros();
        return bigDecimal.toString();
    }

    // Yousif
    /**
     * Converts a numeric amount to a currency-formatted string using the default locale.
     * <p>
     * For example, an input of {@code 123.45} in a US locale would return {@code "$1,23.45"}.
     *
     * @param amt the monetary amount to format
     * @return a string representing the formatted currency value
     */
    public static String toCurrency(double amt) {
        NumberFormat formatter = NumberFormat.getCurrencyInstance();
        return formatter.format(amt);
    }

    // Lizbeth
    /**
     * Formats a LocalDate object into a long date string representation.
     * The format follows the localized LONG format style, which typically includes
     * the full month name, day, and year (e.g., January 1, 2025).
     *
     * @param date the LocalDate to be formatted
     * @return a String representation of the date in the localized long format
     * @throws NullPointerException if date is null
     *
     * @example formatDateLong(LocalDate.of(2025, 1, 1)) returns "January 1, 2025"
     * @example formatDateLong(LocalDate.of(2025, 12, 25)) returns "December 25, 2025"
     */
    public static String formatDateLong(LocalDate date) {
        DateTimeFormatter dateFormatOutput = DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG);
        return dateFormatOutput.format(date);
    }

    //Edward
    /**
     * Formats a LocalDate object into a short date string representation.
     * The format follows the pattern M/d/yyyy where:
     * - M represents the month (1-12) without leading zeros
     * - d represents the day of month (1-31) without leading zeros
     * - yyyy represents the four-digit year
     *
     * @param date the LocalDate to be formatted
     * @return a String representation of the date in M/d/yyyy format
     * @throws NullPointerException if date is null
     *
     * @example formatDateShort(LocalDate.of(2025, 3, 5)) returns "3/5/2025"
     * @example formatDateShort(LocalDate.of(2025, 12, 25)) returns "12/25/2025"
     */
    public static String formatDateShort(LocalDate date) {
        DateTimeFormatter dateFormatOutput = DateTimeFormatter.ofPattern("M/d/yyyy");
        return dateFormatOutput.format(date);
    }

    // Hunter
    /**
     * Checks a date to see if it is in the past
     * @param date Date to be checked if it is in the past or future
     * @throws IllegalArgumentException if the date is null
     * @return boolean true is the date is in the past, false elsewise
     */
    public static boolean isDateInThePast(LocalDate date) {
        if (date == null) {
            throw new IllegalArgumentException("Date cannot be null");
        }
        return date.isBefore(LocalDate.now()); // Check if the date is before today
    }

    /*
     * Determines whether a given date is within a range.
     * @param date: the date being tested
     * @param startDate: the starting date of the range (inclusive)
     * @param endDate: the ending date of the range (inclusive)
     * @return boolean: true if date is within range, false otherwise
     */
    public static boolean isDateInRange(LocalDate date, LocalDate startDate, LocalDate endDate) {
        if (date == null || startDate == null || endDate == null) {
            throw new IllegalArgumentException("None of the dates can be null");
        }

        return (date.isEqual(startDate) || date.isAfter(startDate)) &&
                (date.isEqual(endDate) || date.isBefore(endDate));
    }
}
