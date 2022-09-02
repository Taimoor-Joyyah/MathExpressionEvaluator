package com.company.operation.operator;

import com.company.operation.operand.Operand;
import com.company.operation.OperationComponent;

public interface Operator extends OperationComponent {
    double evaluate(Operand left, Operand right);
    OperatorType getType();
}
