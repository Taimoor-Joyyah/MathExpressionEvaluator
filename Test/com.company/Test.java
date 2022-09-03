package com.company;

import com.company.operation.OperationParser;

public class Test {
    public static void main(String[] args) {
        var operation1 = OperationParser.StringToOperation
                ("(-8)+(45-9)*((7-(6/-5)*9*8)+9/-4)");
        System.out.format("%.2f\n", operation1.calculate());
        System.out.println();
        var operation2 = OperationParser.StringToOperation("-80");
        System.out.format("%.2f\n", operation2.calculate());
    }
}
