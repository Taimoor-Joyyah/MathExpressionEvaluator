package com.company.operation;

import com.company.operation.formatter.ExpressionFormatting;
import com.company.operation.formatter.Report;
import com.company.operation.formatter.StringFormatting;
import com.company.operation.formatter.Validator;
import com.company.operation.operand.DoubleOperand;
import com.company.operation.operand.Operand;
import com.company.operation.operand.OperationOperand;
import com.company.operation.operator.Operator;

import java.util.List;

public class OperationParser {
    private OperationParser() {
    }

    public static Operation StringToOperation(String format) {
        List<OperationComponent> components = StringFormatting.componentParser(format);
        System.out.print("Expression: ");
        Report.displayComponents(components);
        Validator.validatingExpression(components);
        ExpressionFormatting.leftOrdering(components);
        ExpressionFormatting.removeBrackets(components);
        System.out.print("LeftOrdered: ");
        Report.displayComponents(components);
        System.out.print("Result: ");
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
