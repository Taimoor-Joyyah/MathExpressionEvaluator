package com.company.operation.operation.operator;

import com.company.operation.operation.operand.Operand;

class Addition implements Operator {
    @Override
    public double evaluate(Operand left, Operand right) {
        return left.getValue() + right.getValue();
    }

    @Override
    public OperatorType getType() {
        return OperatorType.ADDITION;
    }

    @Override
    public String toString() {
        return "+";
    }
}
