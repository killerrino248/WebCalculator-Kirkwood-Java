package edu.kirkwood.controller;

import edu.kirkwood.model.Fraction;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import static edu.kirkwood.shared.Helpers.isValidInteger;

@WebServlet(value="/fraction")
public class FractionServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("WEB-INF/fraction.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Step 1: Get parameters
        String num1 = req.getParameter("num1");
        String den1 =  req.getParameter("den1");
        String operator = req.getParameter("operator");
        String num2 = req.getParameter("num2");
        String den2 = req.getParameter("den2");
        // Step 2: Set parameters as attributes on the request object
        req.setAttribute("num1",num1);
        req.setAttribute("den1",den1);
        req.setAttribute("operator",operator);
        req.setAttribute("num2",num2);
        req.setAttribute("den2",den2);
        // Step 4: Validate the data
        boolean errorsFound = false;
        if(num1 == null || num1.isEmpty()) {
            errorsFound = true;
            req.setAttribute("num1Error", "<li>Numerator 1 is required</li>");
        } else if(!isValidInteger(num1)) {
            errorsFound = true;
            req.setAttribute("num1Error", "<li>Numerator 1 is not a valid integer</li>");
        }
        if(den1 == null || den1.isEmpty()) {
            errorsFound = true;
            req.setAttribute("den1Error", "<li>Denominator 1 is required</li>");
        } else if(!isValidInteger(den1)) {
            errorsFound = true;
            req.setAttribute("den1Error", "<li>Denominator 1 is not a valid integer</li>");
        }
        if(operator == null || !operator.matches("(add|subtract|multiply|divide)")) {
            errorsFound = true;
            req.setAttribute("operatorError", "<li>Operator is invalid</li>");
        }
        if(num2 == null || num2.isEmpty()) {
            errorsFound = true;
            req.setAttribute("num2Error", "<li>Numerator 2 is required</li>");
        } else if(!isValidInteger(num2)) {
            errorsFound = true;
            req.setAttribute("num2Error", "<li>Numerator 2 is not a valid integer</li>");
        }
        if(den2 == null || den2.isEmpty()) {
            errorsFound = true;
            req.setAttribute("den2Error", "<li>Denominator 2 is required</li>");
        } else if(!isValidInteger(den2)) {
            errorsFound = true;
            req.setAttribute("den2Error", "<li>Denominator 2 is not a valid integer</li>");
        }
        // Step 5: Validate the Fraction
        Fraction fraction1 = null;
        Fraction fraction2 = null;
        if(!errorsFound) {
            try {
                fraction1 = new Fraction(Integer.valueOf(num1), Integer.valueOf(den1));
            } catch(ArithmeticException e) {
                req.setAttribute("den1Error", "<li>Denominator 1 cannot be zero.</li>");
                errorsFound = true;
            }
            try {
                fraction2 = new Fraction(Integer.valueOf(num2), Integer.valueOf(den2));
            } catch(ArithmeticException e) {
                req.setAttribute("den2Error", "<li>Denominator 2 cannot be zero.</li>");
                errorsFound = true;
            }
        }
        // Step 6: Produce the result
        if(!errorsFound) {
            Fraction fraction3 = null;
            String operatorStr = "";
            if(operator.equals("add")) {
                fraction3 = fraction1.add(fraction2);
                operatorStr = "+";
            } else if(operator.equals("subtract")) {
                fraction3 = fraction1.subtract(fraction2);
                operatorStr = "-";
            } else if(operator.equals("multiply")) {
                fraction3 = fraction1.multiply(fraction2);
                operatorStr = "×";
            } else if(operator.equals("divide")) {
                fraction3 = fraction1.divide(fraction2);
                operatorStr = "÷";
            }
            String result = String.format("%s %s %s = %s", fraction1.toMixedNumber(), operatorStr, fraction2.toMixedNumber(), fraction3.toMixedNumber());
            req.setAttribute("result", result);
        }
        // Step 3: Forward the request and response objects to the JSP
        req.getRequestDispatcher("WEB-INF/fraction.jsp").forward(req, resp);
    }
}
