package com.company.operation.formatter;

import com.company.operation.OperationComponent;
import com.company.operation.operator.Operator;
import com.company.operation.operator.OperatorType;

import java.util.Arrays;
import java.util.List;

public class ExpressionFormatting {
    public static void removeBrackets(List<OperationComponent> components) {
        components.removeIf(component -> component instanceof Bracket);
    }

    public static void leftOrdering(List<OperationComponent> components) {
        ordering(components);
        innerOrdering(components);
    }

    private static void innerOrdering(List<OperationComponent> components) {
        int bracketLevel = 0;
        int start = -1;
        for (int i = 0; i < components.size(); i++)
            if (components.get(i) instanceof Bracket) {
                if (((Bracket) components.get(i)).getBracketType() == BracketType.OPEN) {
                    if (bracketLevel == 0)
                        start = i;
                    bracketLevel++;
                } else {
                    bracketLevel--;
                    if (bracketLevel == 0) {
                        List<OperationComponent> subComponents = components.subList(start + 1, i);
                        leftOrdering(subComponents);
                        for (int j = start + 1, k = 0; j < i; j++, k++)
                            components.set(j, subComponents.get(k));
                    }
                }
            }
    }

    private static void ordering(List<OperationComponent> components) {
        List<OperatorType> operatorList = Arrays.stream(OperatorType.values()).toList();
        for (var operator : operatorList) {
            boolean operatorFound = false;
            int start = 0;
            int bracketLevel = 0;
            for (int index = 0; index < components.size(); index++) {
                if (components.get(index) instanceof Bracket) {
                    if (((Bracket) components.get(index)).getBracketType() == BracketType.OPEN)
                        bracketLevel++;
                    else
                        bracketLevel--;
                }
                if (bracketLevel == 0) {
                    if (operatorFound) {
                        if (components.get(index) instanceof Operator &&
                                operatorList.indexOf(((Operator) components.get(index)).getType()) >=
                                        operatorList.indexOf(operator)) {
                            operatorFound = false;
                            swap(components, start, index);
                            index--;
                        } else if (index + 1 == components.size())
                            swap(components, start, index + 1);
                    } else if ((components.get(index) instanceof Operator) &&
                            ((Operator) components.get(index)).getType() == operator) {
                        operatorFound = true;
                        start = index + 1;
                    }
                }
            }
        }
    }

    private static void swap(List<OperationComponent> components, int start, int stop) {
        for (int index = start; index < stop; index++) {
            var temp = components.get(index);
            components.set(index, components.get(index - 1));
            components.set(index - 1, temp);
        }
    }

}
