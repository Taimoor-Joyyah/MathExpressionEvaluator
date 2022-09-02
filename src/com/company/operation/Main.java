package com.company.operation;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter Expression: ");
        Operation operation = OperationFormat.StringToOperation(scanner.nextLine());
        System.out.println(operation.calculate());
    }
}
