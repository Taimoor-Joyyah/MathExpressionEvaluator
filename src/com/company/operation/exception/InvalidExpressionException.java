package com.company.operation.exception;

public class InvalidExpressionException extends Exception{
    public InvalidExpressionException() {
        super("Invalid Expression Found!");
    }

    public InvalidExpressionException(String message) {
        super(message);
    }
}
