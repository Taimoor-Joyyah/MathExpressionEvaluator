package com.company;

public interface Operator extends OperationComponent {
    double evaluate(Operand left, Operand right);
}
