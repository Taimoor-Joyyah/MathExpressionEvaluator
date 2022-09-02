package com.company.operation;

import com.company.operation.operand.DoubleOperand;
import com.company.operation.operand.Operand;
import com.company.operation.operand.OperationOperand;
import com.company.operation.operator.Operator;
import com.company.operation.operator.OperatorFactory;
import com.company.operation.operator.OperatorType;

import java.util.*;

public class OperationFormat {
    private OperationFormat() {
    }

    public static Operation StringToOperation(String format) {
        List<OperationComponent> components = declaration(tokenizing(format));
        System.out.print("Expression: ");
        displayComponents(components);
        leftOrdering(components);
        removeBrackets(components);
        System.out.print("LeftOrdered: ");
        displayComponents(components);
        System.out.print("Result: ");
        return createOperation(components);
    }

    private static void removeBrackets(List<OperationComponent> components) {
        components.removeIf(component -> component instanceof Bracket);
    }

    private static void displayComponents(List<OperationComponent> components) {
        StringBuilder result = new StringBuilder();
        for (var component : components)
            result.append(component).append(" ");
        System.out.println(result);
    }

    private static Operation createOperation(List<OperationComponent> components) {
        if (components.size() == 1)
            return new Operation((DoubleOperand) components.get(0), new DoubleOperand(0), getOperator("+"));
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

    private static Operator getOperator(String operator) {
        return switch (operator) {
            case "+" -> OperatorFactory.getAddition();
            case "-" -> OperatorFactory.getSubtraction();
            case "*" -> OperatorFactory.getMultiplication();
            case "/" -> OperatorFactory.getDivision();
            default -> null;
        };
    }

    private static boolean validBrackets(List<OperationComponent> components) {
        int bracketLevel = 0;
        for (var component : components) {
            if (component instanceof Bracket) {
                if (((Bracket) component).getBracketType() == BracketType.OPEN)
                    bracketLevel++;

                else
                    bracketLevel--;
                if (bracketLevel < 0)
                    return false;
            }
        }
        return true;
    }

    private static void leftOrdering(List<OperationComponent> components) {
        var brackets = components.stream()
                .filter(c -> c instanceof Bracket)
                .map(c -> ((Bracket) c).getBracketType()).toList();
        if (components.size() % 2 != 1 ||
                brackets.stream().filter(e -> e == BracketType.OPEN).count()
                        != brackets.stream().filter(e -> e == BracketType.CLOSE).count() || !validBrackets(components))
            throw new ArithmeticException("Invalid Expression found");
        Map<OperatorType, Integer> operatorPrecedence = new HashMap<>() {{
            put(OperatorType.DIVISION, 0);
            put(OperatorType.MULTIPLICATION, 1);
            put(OperatorType.ADDITION, 2);
            put(OperatorType.SUBTRACTION, 3);
        }};

        var operatorList = operatorPrecedence.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .toList();

        ordering(components, operatorPrecedence, operatorList);
    }

    private static void ordering(List<OperationComponent> components,
                                 Map<OperatorType, Integer> operatorPrecedence,
                                 List<OperatorType> operatorList) {
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
                                operatorPrecedence.get(((Operator) components.get(index)).getType()) >=
                                        operatorPrecedence.get(operator)) {
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
        int bracketLevel = 0;
        int start = -1;
        for (int i = 0; i < components.size(); i++) {
            if (components.get(i) instanceof Bracket) {
                if (((Bracket) components.get(i)).getBracketType() == BracketType.OPEN) {
                    if (bracketLevel == 0)
                        start = i;
                    bracketLevel++;
                } else {
                    bracketLevel--;
                    if (bracketLevel == 0) {
                        List<OperationComponent> subComponents = components.subList(start + 1, i);
                        ordering(subComponents, operatorPrecedence, operatorList);
                        for (int j = start + 1, k = 0; j < i; j++, k++)
                            components.set(j, subComponents.get(k));
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

    private static List<String> tokenizing(String format) {

        var singleChars = new String[]{"+", "-", "*", "/", "(", ")"};
        for (var character : singleChars) {
            format = format.replace(character, " " + character + " ");
        }
        return Arrays.stream(format.split(" ")).filter(s -> !s.isEmpty()).toList();
    }

    private static List<OperationComponent> declaration(List<String> strings) {
        List<OperationComponent> components = new ArrayList<>();
        ComponentType previous = ComponentType.OPEN;
        for (var string : strings) {
            if (isBracket(string)) {
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
            } else if (isOperator(string)) {
                if (previous != ComponentType.DOUBLE && previous != ComponentType.CLOSE) {
                    if (previous == ComponentType.NEGATIVE)
                        throw new UnsupportedOperationException("invalid operation are not allowed");
                    else if (string.equals("-"))
                        previous = ComponentType.NEGATIVE;
                } else {
                    components.add(getOperator(string));
                    previous = ComponentType.OPERATOR;
                }
            } else if (isDouble(string)) {
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

    private static boolean isDouble(String string) {
        for (char character : string.toCharArray())
            if (!(Character.isDigit(character) || character == '.'))
                return false;
        return true;
    }

    private static boolean isBracket(String string) {
        return string.equals("(") || string.equals(")");
    }

    private static boolean isOperator(String string) {
        return string.equals("+") ||
                string.equals("-") ||
                string.equals("*") ||
                string.equals("/");
    }
}
