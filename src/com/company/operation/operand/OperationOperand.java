package com.company.operation.operand;

import com.company.operation.Operation;

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
