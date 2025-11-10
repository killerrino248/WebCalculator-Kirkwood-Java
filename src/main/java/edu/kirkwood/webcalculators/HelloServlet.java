package edu.kirkwood.webcalculators;

import java.io.*;

import edu.kirkwood.helpers.Helpers;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet(value = "/hello")
public class HelloServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("WEB-INF/hello.jsp").forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Step 1: Get the raw data from the form
        String num1 = req.getParameter("num1");
        String num2 = req.getParameter("num2");
        // Step 2: Set raw data as a request attribute
        req.setAttribute("num1",num1);
        req.setAttribute("num2",num2);
        // Step 4: Validate the raw data
        boolean errorsFound = false;
        // Step 4a: Determine required values
        // Step 4b: Are the values valid numbers
        if(num1 == null || num1.isEmpty()) {
            req.setAttribute("num1Error", "Number 1 is required");
            errorsFound = true;
        } else if(!isValidNumber(num1)) {
            req.setAttribute("num1Error", "Number 1 is not a valid number");
            errorsFound = true;
        }
        if(num2 == null || num2.isEmpty()) {
            req.setAttribute("num2Error", "Number 2 is required");
            errorsFound = true;
        } else if(!isValidNumber(num2)) {
            req.setAttribute("num2Error", "Number 2 is not a valid number");
            errorsFound = true;
        }
        // Process the result
        if(!errorsFound) {
            double sum = Double.valueOf(num1) + Double.valueOf(num2);
            String result = String.format("%s + %s = %s", num1, num2, Helpers.round(sum, 10));
            req.setAttribute("result", result);
        }
        // Step 3: Forward the req and resp objects to the JSP
        req.getRequestDispatcher("WEB-INF/hello.jsp").forward(req,resp);
    }

    public static boolean isValidNumber(String num) {
        try {
            Double.parseDouble(num);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

}