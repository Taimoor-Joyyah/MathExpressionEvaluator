package com.company.operation.operator;

import com.company.operation.OperationComponent;
import com.company.operation.operand.Operand;

public interface Operator extends OperationComponent {
    double evaluate(Operand left, Operand right);

    OperatorType getType();
}
