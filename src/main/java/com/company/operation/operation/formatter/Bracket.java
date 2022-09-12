package com.company.operation.operation.formatter;

import com.company.operation.operation.OperationComponent;

public class Bracket implements OperationComponent {
    private final BracketType bracketType;

    public Bracket(BracketType bracketType) {
        this.bracketType = bracketType;
    }

    public BracketType getBracketType() {
        return bracketType;
    }

    @Override
    public String toString() {
        return switch (bracketType) {
            case OPEN -> "(";
            case CLOSE -> ")";
        };
    }
}
