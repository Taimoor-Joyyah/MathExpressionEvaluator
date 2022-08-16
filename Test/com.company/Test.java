package com.company;

import com.company.operation.OperationFormat;

public class Test {
    public static void main(String[] args) {
        var operation = OperationFormat.StringToOperation("-8 + 45 - 9 * 7 - 6 / -5 * 9 * 8 + 9 / -4");
        System.out.format("%.2f", operation.calculate());
    }
}
