package edu.kirkwood.model;

import java.util.Objects;

/**
 * Represents a fraction with an integer numerator and denominator.
 * This class provides methods for fraction arithmetic, simplification,
 * and comparison.
 */
public class Fraction implements Comparable<Fraction> {
    private int numerator;
    private int denominator;

    /**
     * Default constructor.
     * Initializes a new fraction to 1/1.
     */
    public Fraction() {
        this.numerator = 1;
        this.denominator = 1;
    }

    /**
     * Constructs a fraction with a specified numerator and denominator.
     *
     * @param numerator   the numerator of the fraction
     * @param denominator the denominator of the fraction
     */
    public Fraction(int numerator, int denominator) {
        setNumerator(numerator);
        setDenominator(denominator);
    }

    /**
     * Gets the numerator of the fraction.
     *
     * @return the numerator
     */
    public int getNumerator() {
        return numerator;
    }

    /**
     * Sets the numerator of the fraction.
     *
     * @param numerator the new numerator
     */
    public void setNumerator(int numerator) {
        this.numerator = numerator;
    }

    /**
     * Gets the denominator of the fraction.
     *
     * @return the denominator
     */
    public int getDenominator() {
        return denominator;
    }

    /**
     * Sets the denominator of the fraction.
     *
     * @param denominator the new denominator
     * @throws ArithmeticException if the denominator is zero
     */
    public void setDenominator(int denominator) {
        if(denominator == 0) {
            throw new ArithmeticException("Denominator cannot be zero");
        }
        if(denominator < 0) {
            numerator *= -1;
            denominator *= -1;
        }
        this.denominator = denominator;
    }

    /**
     * Returns a string representation of the fraction in the format "numerator/denominator".
     *
     * @return a string representation of the fraction
     */
    @Override
    public String toString() {
        return numerator + "/" +  denominator;
    }

    /**
     * Compares this fraction to another fraction.
     *
     * @param o the other Fraction to be compared.
     * @return a negative integer, zero, or a positive integer.
     */
    @Override
    public int compareTo(Fraction o) {
        // The products of the numerators and denominators could potentially exceed the maximum value of an int.
        // By casting to a long before multiplication, we prevent this integer overflow.
        long thisNumerator = (long)this.numerator;
        long thisDenominator = (long)this.denominator;
        long otherNumerator = (long)o.numerator;
        long otherDenominator = (long)o.denominator;
        // The comparison n1/d1 vs n2/d2 is equivalent to comparing the integer results of n1 * d2 vs n2 * d1.
        long a = thisNumerator * otherDenominator;
        long b = otherNumerator * thisDenominator;
        // Using Long.compare(a, b) is the standard and safest way to compare two long values. It simply returns -1, 0, or 1 based on the comparison.
        return Long.compare(a, b);
    }
    /*
        @Override
        public boolean equals(Object o) {
            if (o == null || getClass() != o.getClass()) return false;
            Fraction fraction = (Fraction) o;
            return numerator == fraction.numerator && denominator == fraction.denominator;
        }
    */
    @Override
    public boolean equals(Object other)
    {
        boolean isEqual = false;
        if (this != null && other != null)
        {
            if (this instanceof Fraction && other instanceof Fraction)
            {
                this.simplify();
                ((Fraction)other).simplify();
                isEqual = ((this.getNumerator() == ((Fraction) other).getNumerator()) &&
                        (this.getDenominator() == ((Fraction) other).getDenominator()));
            }
        }
        return isEqual;
    }

    @Override
    public int hashCode() {
        return Objects.hash(numerator, denominator);
    }

    /**
     * Calculates the greatest common divisor (GCD) of two integers.
     *
     * @param a the first integer
     * @param b the second integer
     * @return the greatest common divisor of a and b
     */
    public static int gcd(int a, int b) {
        // Source: https://stackoverflow.com/a/4009247/6629315
        if (b==0) {
            // Source: https://stackoverflow.com/a/30693436/6629315
            return Math.abs(a);
        }
        return gcd(b,a % b);
    }

    /**
     * Calculates the least common multiple (LCM) of two integers.
     *
     * @param a the first integer
     * @param b the second integer
     * @return the least common multiple of a and b
     */
    public static int lcm(int a, int b) {
        if(a == 0 || b == 0) {
            return 0;
        }
        // Source: https://stackoverflow.com/a/4202114/6629315
        return a * (b / gcd(a, b));
    }

    /**
     * Simplifies this fraction to its lowest terms by dividing the numerator
     * and denominator by their greatest common divisor.
     */
    public void simplify()
    {
        int gcdNumDen = Fraction.gcd(this.getNumerator(), this.getDenominator());
        this.setNumerator(this.getNumerator() / gcdNumDen);
        this.setDenominator(this.getDenominator() / gcdNumDen);
    }

    /**
     * Converts this fraction to a mixed number string representation (e.g., "1 2/3").
     * If the fraction is a proper fraction, it returns the fraction itself.
     *
     * @return a string representation of the fraction as a mixed number
     */
    public String toMixedNumber() {
        int whole = this.numerator / this.denominator;
        int remainder = this.numerator % this.denominator;
        if (remainder == 0) {
            return String.valueOf(whole);
        }
        if (whole == 0) {
            return this.toString();
        }
        return String.valueOf(whole) + " " + String.valueOf((Math.abs(remainder))) + "/" + this.denominator;
    }

    /**
     * Adds another fraction to this fraction.
     *
     * @param other the fraction to add
     * @return a new Fraction object representing the sum
     */
    public Fraction add(Fraction other) {
        Fraction result = new Fraction();
        result.numerator = this.numerator * other.denominator + this.denominator * other.numerator;
        result.denominator = this.denominator * other.denominator;
        result.simplify();
        return result;
    }

    /**
     * Subtracts another fraction from this fraction.
     *
     * @param other the fraction to subtract
     * @return a new Fraction object representing the difference
     */
    public Fraction subtract(Fraction other) {
        int newNumerator = numerator * other.denominator - denominator * other.numerator;
        int newDenominator = denominator * other.denominator;

        Fraction result =  new Fraction(newNumerator, newDenominator);

        result.simplify();

        return result;
    }

    /**
     * Multiplies this fraction by another fraction.
     *
     * @param other the fraction to multiply by
     * @return a new Fraction object representing the product
     */
    public Fraction multiply(Fraction other) {
        int newNumerator = this.numerator * other.numerator;
        int newDenominator = this.denominator * other.denominator;
        Fraction result = new Fraction(newNumerator, newDenominator);
        result.simplify();
        return result;
    }

    /**
     * Divides this fraction by another fraction.
     *
     * @param other the fraction to divide by (the divisor)
     * @return a new Fraction object representing the quotient
     */
    public Fraction divide(Fraction other) { //-2/5 5/-3
        Fraction result = new Fraction(numerator * other.denominator, denominator * other.numerator);
        result.simplify();
        return result;
    }
}

