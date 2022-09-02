package com.company.operation;

import com.company.operation.operand.Operand;
import com.company.operation.operator.Operator;

public class Operation {
    private final Operand left;
    private final Operator operator;
    private final Operand right;

    public Operation(Operand left, Operand right, Operator operator) {
        this.left = left;
        this.right = right;
        this.operator = operator;
    }

    public double calculate() {
        return operator.evaluate(left, right);
    }

    @Override
    public String toString() {
        return left + " " + operator + " " + right;
    }
}
