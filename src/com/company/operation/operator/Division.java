package com.company.operation.operator;

import com.company.operation.operand.Operand;

public class Division implements Operator{
    @Override
    public double evaluate(Operand left, Operand right) {
        return left.getValue() / right.getValue();
    }

    @Override
    public String getSymbol() {
        return "/";
    }
}
