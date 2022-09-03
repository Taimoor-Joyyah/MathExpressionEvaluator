package com.company.operation.formatter;

import com.company.operation.OperationComponent;
import com.company.operation.operand.DoubleOperand;
import com.company.operation.operator.Operator;
import com.company.operation.operator.OperatorFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StringFormatting {
    public static List<String> tokenizing(String format) {
        for (var character : new String[]{"+", "-", "*", "/", "(", ")"})
            format = format.replace(character, " " + character + " ");
        return Arrays.stream(format.split(" ")).filter(s -> !s.isEmpty()).toList();
    }

    public static List<OperationComponent> declaration(List<String> strings) {
        List<OperationComponent> components = new ArrayList<>();
        ComponentType previous = ComponentType.OPEN;
        for (var string : strings) {
            if (Validator.isBracket(string)) {
                if (string.equals("(")) {
                    if (previous != ComponentType.OPERATOR && previous != ComponentType.OPEN)
                        throw new UnsupportedOperationException("invalid operation are not allowed");
                    else {
                        components.add(new Bracket(BracketType.OPEN));
                        previous = ComponentType.OPEN;
                    }
                } else {
                    if (previous != ComponentType.DOUBLE && previous != ComponentType.CLOSE)
                        throw new UnsupportedOperationException("invalid operation are not allowed");
                    else {
                        components.add(new Bracket(BracketType.CLOSE));
                        previous = ComponentType.CLOSE;
                    }
                }
            } else if (Validator.isOperator(string)) {
                if (previous != ComponentType.DOUBLE && previous != ComponentType.CLOSE) {
                    if (previous == ComponentType.NEGATIVE)
                        throw new UnsupportedOperationException("invalid operation are not allowed");
                    else if (string.equals("-"))
                        previous = ComponentType.NEGATIVE;
                } else {
                    components.add(getOperator(string));
                    previous = ComponentType.OPERATOR;
                }
            } else if (Validator.isDouble(string)) {
                if (previous == ComponentType.CLOSE || previous == ComponentType.DOUBLE)
                    throw new UnsupportedOperationException("invalid operation are not allowed");
                else {
                    components.add(new DoubleOperand(
                            (previous != ComponentType.NEGATIVE ? 1 : -1) * Double.parseDouble(string)));
                    previous = ComponentType.DOUBLE;
                }
            }
        }
        return components;
    }

    public static List<OperationComponent> componentParser(String format) {
        return declaration(tokenizing(format));
    }

    public static Operator getOperator(String operator) {
        return switch (operator) {
            case "+" -> OperatorFactory.getAddition();
            case "-" -> OperatorFactory.getSubtraction();
            case "*" -> OperatorFactory.getMultiplication();
            case "/" -> OperatorFactory.getDivision();
            default -> null;
        };
    }
}