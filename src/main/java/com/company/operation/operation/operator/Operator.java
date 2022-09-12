package com.company.operation.operation.operator;

import com.company.operation.operation.OperationComponent;
import com.company.operation.operation.operand.Operand;

public interface Operator extends OperationComponent {
    double evaluate(Operand left, Operand right);

    OperatorType getType();
}
