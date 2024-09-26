package org.example;

import java.util.ArrayList;
import java.util.Comparator;

public class Task3 {
    public static void main(String[] args) {
        ArrayList<Employee> arr = new ArrayList<>();
        arr.add(new Employee("Ivan", 23, "IT", 86000.0));
        arr.add(new Employee("Maxim", 24, "HR", 67000.0));
        arr.add(new Employee("Kirill", 22, "IT", 93000.0));
        arr.add(new Employee("Petr", 21, "PM", 78000.0));
        arr.add(new Employee("Alex", 25, "PO", 95000.0));
        var sorted = arr.stream().sorted(Comparator.comparing(Employee::getSalary));
        for(var e : sorted.toList())
            System.out.println(e);
    }
}
