package com.company.operation.operator;

import com.company.operation.operand.Operand;

class Multiplication implements Operator {
    @Override
    public double evaluate(Operand left, Operand right) {
        return left.getValue() * right.getValue();
    }

    @Override
    public OperatorType getType() {
        return OperatorType.MULTIPLICATION;
    }

    @Override
    public String toString() {
        return "*";
    }
}
