package com.company;

public class Subtraction implements Operator{
    @Override
    public double evaluate(Operand left, Operand right) {
        return left.getValue() - right.getValue();
    }
}
