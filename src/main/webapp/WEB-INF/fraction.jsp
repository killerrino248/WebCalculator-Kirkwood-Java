<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!doctype html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Fraction Calculator</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
</head>
<body>

<div class="container my-4">
    <h1>Fraction Calculator</h1>
    <p class="lead">Enter two fractions and press submit to calculate the sum.</p>
    <form method="POST" action="fraction">
        <div class="row">
            <div class="col-2">
                <div class="form-group mb-2 pb-2 border-bottom border-5">
                    <input type="text" class="form-control" placeholder="Numerator 1" aria-label="Numerator 1" name="num1" value="${num1}">
                </div>
                <div class="form-group mb-2">
                    <input type="text" class="form-control" placeholder="Denominator 1" aria-label="Denominator 1" name="den1" value="${den1}">
                </div>
            </div>
            <div class="col-1 d-flex justify-content-center align-items-center display-3">
                <select class="form-select form-select-lg" aria-label="Mathematical operator" name="operator">
                    <option <c:if test="${operator == 'add'}">selected</c:if> value="add">+</option>
                    <option <c:if test="${operator == 'subtract'}">selected</c:if> value="subtract">-</option>
                    <option <c:if test="${operator == 'multiply'}">selected</c:if> value="multiply">×</option>
                    <option <c:if test="${operator == 'divide'}">selected</c:if> value="divide">÷</option>
                </select>
            </div>
            <div class="col-2">
                <div class="form-group mb-2 pb-2 border-bottom border-5">
                    <input type="text" class="form-control" placeholder="Numerator 2" aria-label="Numerator 2" name="num2" value="${num2}">
                </div>
                <div class="form-group mb-2">
                    <input type="text" class="form-control" placeholder="Denominator 2" aria-label="Denominator 2" name="den2" value="${den2}">
                </div>
            </div>
        </div>

        <button type="submit" class="btn btn-primary">Submit</button>
    </form>
    <ul class="text-danger">
        ${num1Error}
        ${den1Error}
        ${operatorError}
        ${num2Error}
        ${den2Error}
    </ul>
    <div class="text-success fs-1">${result}</div>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
</body>
</html>