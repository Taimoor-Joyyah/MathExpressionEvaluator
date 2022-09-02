package com.company.operation.operand;

public class DoubleOperand implements Operand{
    private final double operand;

    public DoubleOperand(double operand) {
        this.operand = operand;
    }

    @Override
    public double getValue() {
        return operand;
    }

    @Override
    public String toString() {
        return Double.toString(operand);
    }
}
