package com.company.operation.formatter;

import com.company.operation.OperationComponent;

import java.util.List;

public class Report {
    public static void displayComponents(List<OperationComponent> components) {
        StringBuilder result = new StringBuilder();
        for (var component : components)
            result.append(component).append(" ");
        System.out.println(result);
    }
}
