package com.company.operation.operand;

public class DoubleOperand implements Operand {
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
        if (operand == (double) Math.round(operand))
            return String.valueOf((int) operand);
        else
            return Double.toString(operand);
    }
}
