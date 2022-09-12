package com.company.operation.operation.operator;

import com.company.operation.operation.operand.Operand;

class Subtraction implements Operator {
    @Override
    public double evaluate(Operand left, Operand right) {
        return left.getValue() - right.getValue();
    }

    @Override
    public OperatorType getType() {
        return OperatorType.SUBTRACTION;
    }

    @Override
    public String toString() {
        return "-";
    }
}
