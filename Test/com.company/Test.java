package com.company;

public class Test {

    public static void main(String[] args) {
        var operation = new Operation(new OperationOperand(new Operation(new DoubleOperand(40), new DoubleOperand(24), new Multiplication())), new DoubleOperand(45), new Addition());
        System.out.println(operation.calculate());
    }
}
