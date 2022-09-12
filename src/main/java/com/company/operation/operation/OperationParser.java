package com.company.operation.operation;

import com.company.operation.operation.exception.InCompleteException;
import com.company.operation.operation.exception.InvalidExpressionException;
import com.company.operation.operation.formatter.ExpressionFormatting;
import com.company.operation.operation.formatter.StringFormatting;
import com.company.operation.operation.formatter.Validator;
import com.company.operation.operation.formatter.Report;
import com.company.operation.operation.operand.DoubleOperand;
import com.company.operation.operation.operand.Operand;
import com.company.operation.operation.operand.OperationOperand;
import com.company.operation.operation.operator.Operator;

import java.util.List;

public class OperationParser {
    private OperationParser() {
    }

    public static Operation StringToOperation(String format) throws InvalidExpressionException, InCompleteException {
        List<OperationComponent> components = StringFormatting.componentParser(format);
        Validator.validatingExpression(components);
        ExpressionFormatting.leftOrdering(components);
        Report.displayComponents(components);
        ExpressionFormatting.removeBrackets(components);
        return createOperation(components);
    }

    private static Operation createOperation(List<OperationComponent> components) {
        if (components.size() == 1)
            return new Operation((DoubleOperand) components.get(0), new DoubleOperand(0), StringFormatting.getOperator("+"));
        for (int index = 2; components.size() != 1; index++)
            if (components.get(index) instanceof Operator operator) {
                exchangeComponent(components, index, operator);
                index -= 2;
            }
        return ((OperationOperand) components.get(0)).getOperand();
    }

    private static void exchangeComponent(List<OperationComponent> components, int index, Operator operator) {
        Operand left = (Operand) components.get(index - 2);
        Operand right = (Operand) components.get(index - 1);
        components.set(index, new OperationOperand(new Operation(left, right, operator)));
        components.remove(index - 1);
        components.remove(index - 2);
    }
}
