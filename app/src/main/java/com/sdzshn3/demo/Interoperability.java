package com.sdzshn3.demo;

import java.util.ArrayList;
import java.util.List;

public class Interoperability {
    public static List<Object> processData(List<Employee> employees, List<Student> students) {
        ArrayList<Object> objects = new ArrayList<>();
        int lastPositionOfStudent = 0;
        for (int i = 0; i < employees.size(); i++) {
            lastPositionOfStudent = i;
            objects.add(employees.get(i));

            // If students are less than employees, below try catch will catch the exception
            try {
                objects.add(students.get(i));
            } catch (IndexOutOfBoundsException ignored) {}
        }

        // IF employees are less than students, we add remaining remaining students here
        if (employees.size() < students.size()) {
            for (int i = lastPositionOfStudent; i < students.size(); i++) {
                objects.add(students.get(i));
            }
        }

        return objects;
    }
}
