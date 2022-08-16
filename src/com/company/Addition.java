package com.company;

public class Addition implements Operator{
    @Override
    public double evaluate(Operand left, Operand right) {
        return left.getValue() + right.getValue();
    }
}
