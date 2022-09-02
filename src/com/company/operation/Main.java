package com.company.operation;

import com.company.operation.operator.Operator;
import com.company.operation.operator.OperatorFactory;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Operation operation = OperationFormat.StringToOperation(scanner.next());
        System.out.println(operation.calculate());
        Operator add = OperatorFactory.getAddition();
    }
}
