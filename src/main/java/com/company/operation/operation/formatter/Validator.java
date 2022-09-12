package com.company.operation.operation.formatter;

import com.company.operation.operation.OperationComponent;
import com.company.operation.operation.exception.InCompleteException;

import java.util.List;

public class Validator {
    public static boolean validBrackets(List<OperationComponent> components) {
        int bracketLevel = 0;
        for (var component : components) {
            if (component instanceof Bracket) {
                if (((Bracket) component).getBracketType() == BracketType.OPEN)
                    bracketLevel++;
                else {
                    bracketLevel--;
                    if (bracketLevel < 0)
                        return false;
                }
            }
        }
        return true;
    }

    public static void validatingExpression(List<OperationComponent> components) throws InCompleteException {
        var brackets = components.stream()
                .filter(c -> c instanceof Bracket)
                .map(c -> ((Bracket) c).getBracketType()).toList();
        if (components.size() % 2 != 1 ||
                brackets.stream().filter(e -> e == BracketType.OPEN).count() != brackets.stream().filter(e -> e == BracketType.CLOSE).count() ||
                !validBrackets(components))
            throw new InCompleteException("Incomplete Expression found");
    }

    public static boolean isDouble(String string) {
        for (char character : string.toCharArray())
            if (!(Character.isDigit(character) || character == '.'))
                return false;
        return true;
    }

    public static boolean isBracket(String string) {
        return string.equals("(") || string.equals(")");
    }

    public static boolean isOperator(String string) {
        return string.equals("+") ||
                string.equals("-") ||
                string.equals("*") ||
                string.equals("/");
    }
}
