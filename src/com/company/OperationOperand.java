package com.company;

public class OperationOperand implements Operand{
    private final Operation operand;

    public OperationOperand(Operation operand) {
        this.operand = operand;
    }

    @Override
    public double getValue() {
        return operand.calculate();
    }
}
