package com.company;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class OperationFormat {
    private OperationFormat() {
    }

    public static Operation StringToOperation(String format) {
        //Tokenizing
        List<String> tokens = tokenizing(format);
        System.out.println(tokens);
        //Parsing and validating
        leftOrdering(tokens);
        System.out.println(tokens);
        //creating Operation
        return createOperation(tokens);
    }

    private static Operation createOperation(List<String> tokens) {
        List<OperationComponent> components = new ArrayList<>();

        for (String token : tokens) {
            char[] tokenChar = token.toCharArray();
            if (tokenChar.length == 1 && isOperator(tokenChar[0]))
                components.add(getOperator(tokenChar[0]));
            else
                components.add(new DoubleOperand(Double.parseDouble(token)));
        }

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

    private static void leftOrdering(List<String> tokens) {
        if (tokens.size() % 2 == 0)
            throw new ArithmeticException("Invalid Expression found");
        String[] operators = {"/", "*", "+", "-"};
        for (var operator : operators) {
            boolean operatorFound = false;
            for (int i = 1; i < tokens.size(); i++) {
                if (operatorFound) {
                    var chars = tokens.get(i).toCharArray();
                    if (chars.length == 1 && isOperator(chars[0])) {
                        var array = Arrays.stream(operators).toList();
                        if (array.indexOf(Character.toString(chars[0])) >= array.indexOf(operator)) {
                            operatorFound = false;
                            i--;
                            continue;
                        }
                    }
                    var temp = tokens.get(i);
                    tokens.set(i, tokens.get(i - 1));
                    tokens.set(i - 1, temp);
                }
                if (tokens.get(i).equals(operator)) {
                    operatorFound = true;
                }
            }
        }
    }

    private static List<String> tokenizing(String format) {
        List<String> tokenList = new ArrayList<>();
        int index = 0;
        while (index < format.length()) {
            StringBuilder token = new StringBuilder();
            boolean isPreviousOperator = true;
            boolean isNegative = false;
            for (; true; index++) {
                char character = format.charAt(index);
                if (character == ' ') {
                    continue;
                }
                else if (isDouble(character)) {
                    token.append(character);
                    isNegative = false;
                    isPreviousOperator = false;
                } else if (isOperator(character)) {
                    if (!isPreviousOperator) {
                        if (!token.isEmpty())
                            tokenList.add(token.toString());
                        tokenList.add(Character.toString(character));
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
                    tokenList.add(token.toString());
                    index++;
                    break;
                }
            }
        }
        return tokenList;
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
