package com.company.operation;

import com.company.operation.exception.InCompleteException;
import com.company.operation.exception.InvalidExpressionException;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws InvalidExpressionException, InCompleteException {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter Expression: ");
        Operation operation = OperationParser.StringToOperation(scanner.nextLine());
        System.out.println(operation.calculate());
    }
}
