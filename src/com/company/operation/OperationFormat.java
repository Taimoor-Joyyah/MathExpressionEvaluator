package com.company.operation;

import com.company.operation.operand.DoubleOperand;
import com.company.operation.operand.Operand;
import com.company.operation.operand.OperationOperand;
import com.company.operation.operator.Operator;
import com.company.operation.operator.OperatorFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class OperationFormat {
    private OperationFormat() {
    }

    public static Operation StringToOperation(String format) {
        //Tokenizing
        List<OperationComponent> components = tokenizing(format);
        System.out.println(components);
        //Parsing and validating
        leftOrdering(components);
        System.out.println(components);
        //creating Operation
        return createOperation(components);
    }

    private static Operation createOperation(List<OperationComponent> components) {
        for (int index = 2; true; index++) {
            if (components.get(index) instanceof Operator operator) {
                Operand left = (Operand) components.get(index - 2);
                Operand right = (Operand) components.get(index - 1);

                var operation = new Operation(left, right, operator);

                if (components.size() == 3)
                    return operation;

                components.set(index, new OperationOperand(operation));
                components.remove(index - 1);
                components.remove(index - 2);

                index -= 2;
            }
        }
    }

    private static Operator getOperator(char operator) {
        return switch (operator) {
            case '+' -> OperatorFactory.getAddition();
            case '-' -> OperatorFactory.getSubtraction();
            case '*' -> OperatorFactory.getMultiplication();
            case '/' -> OperatorFactory.getDivision();
            default -> null;
        };
    }

    private static void leftOrdering(List<OperationComponent> components) {
        if (components.size() % 2 == 0)
            throw new ArithmeticException("Invalid Expression found");
        List<String> OperationPrecedence = Arrays.asList("/", "*", "+", "-");
        for (var operator : OperationPrecedence) {
            boolean operatorFound = false;
            for (int index = 1; index < components.size(); index++) {
                if (operatorFound) {
                    if (components.get(index) instanceof Operator) {
                        if (OperationPrecedence.indexOf(((Operator) components.get(index)).getSymbol()) >=
                                OperationPrecedence.indexOf(operator)) {
                            operatorFound = false;
                            index--;
                            continue;
                        }
                    }
                    var temp = components.get(index);
                    components.set(index, components.get(index - 1));
                    components.set(index - 1, temp);
                } else if ((components.get(index) instanceof Operator) &&
                        ((Operator) components.get(index)).getSymbol().equals(operator)) {
                    operatorFound = true;
                }
            }
        }
    }

    private static List<OperationComponent> tokenizing(String format) {
        List<OperationComponent> components = new ArrayList<>();
        int index = 0;
        while (index < format.length()) {
            StringBuilder token = new StringBuilder();
            boolean isPreviousOperator = true;
            boolean isNegative = false;
            for (; true; index++) {
                char character = format.charAt(index);
                if (character == ' ') {
                    continue;
                } else if (isDouble(character)) {
                    token.append(character);
                    isNegative = false;
                    isPreviousOperator = false;
                } else if (isOperator(character)) {
                    if (!isPreviousOperator) {
                        if (!token.isEmpty()) {
                            components.add(new DoubleOperand(Double.parseDouble(token.toString())));
                        }
                        components.add(getOperator(character));
                        index++;
                        break;
                    } else if (character == '-' && !isNegative) {
                        token.append(character);
                        isNegative = true;
                    } else
                        throw new UnsupportedOperationException("multi operators are not allowed");
                } else
                    throw new UnsupportedOperationException("Invalid Character for Expression found");

                if (index == format.length() - 1) {
                    components.add(new DoubleOperand(Double.parseDouble(token.toString())));
                    index++;
                    break;
                }
            }
        }
        return components;
    }

    private static boolean isDouble(char character) {
        return Character.isDigit(character) || character == '.';
    }

    private static boolean isOperator(char operator) {
        return operator == '+' ||
                operator == '-' ||
                operator == '*' ||
                operator == '/';
    }
}
