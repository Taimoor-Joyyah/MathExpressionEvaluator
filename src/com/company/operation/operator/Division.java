package com.company.operation.operator;

import com.company.operation.operand.Operand;

class Division implements Operator {
    @Override
    public double evaluate(Operand left, Operand right) {
        return left.getValue() / right.getValue();
    }

    @Override
    public OperatorType getType() {
        return OperatorType.DIVISION;
    }

    @Override
    public String toString() {
        return "/";
    }
}
