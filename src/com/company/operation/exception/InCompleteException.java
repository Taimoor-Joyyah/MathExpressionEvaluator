package com.company.operation.exception;

public class InCompleteException extends Exception {
    public InCompleteException() {
        super("Incomplete Expression Found!");
    }

    public InCompleteException(String message) {
        super(message);
    }
}
