package com.company.operation;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Operation operation = OperationFormat.StringToOperation(scanner.next());
        System.out.println(operation.calculate());
    }
}
