package org.example;

public class Calculator {

    public double sum (double x, double y){
        return x + y;
    }

    public double sub (double x, double y){
        return x - y;
    }

    public double div (double x, double y) {
        if (y == 0){
            throw new ArithmeticException("Division by zero");
        }
        return x / y;
    }

    public double mul (double x, double y){
        return x * y;
    }
}
