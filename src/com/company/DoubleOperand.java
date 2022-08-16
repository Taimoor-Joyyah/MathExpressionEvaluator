package com.company;

public class DoubleOperand implements Operand{
    private final double operand;

    public DoubleOperand(double operand) {
        this.operand = operand;
    }

    @Override
    public double getValue() {
        return operand;
    }
}
