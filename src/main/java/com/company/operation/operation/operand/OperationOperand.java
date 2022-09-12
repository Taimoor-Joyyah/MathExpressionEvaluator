package com.company.operation.operation.operand;

import com.company.operation.operation.Operation;

public class OperationOperand implements Operand {
    private final Operation operand;

    public OperationOperand(Operation operand) {
        this.operand = operand;
    }

    @Override
    public double getValue() {
        return operand.calculate();
    }

    public Operation getOperand() {
        return operand;
    }

    @Override
    public String toString() {
        return operand.toString();
    }
}
