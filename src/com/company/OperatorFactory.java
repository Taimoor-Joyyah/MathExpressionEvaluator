package com.company;

public class OperatorFactory {
    private static Addition addition;
    private static Subtraction subtraction;
    private static Multiplication multiplication;
    private static Division division;

    private OperatorFactory() {
    }

    public static Addition getAddition() {
        if (addition == null)
            addition = new Addition();
        return addition;
    }

    public static Subtraction getSubtraction() {
        if (subtraction == null)
            subtraction = new Subtraction();
        return subtraction;
    }

    public static Multiplication getMultiplication() {
        if (multiplication == null)
            multiplication = new Multiplication();
        return multiplication;
    }

    public static Division getDivision() {
        if (division == null)
            division = new Division();
        return division;
    }
}
