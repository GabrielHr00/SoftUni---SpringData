package com.example.demo.demo;

import com.example.demo.demo.EmployeeDto;

import java.util.Set;
import java.util.stream.Collectors;

public class ManagerDto {
    private String firstName;
    private String lastName;
    private Set<EmployeeDto> subordinates;

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setSubordinates(Set<EmployeeDto> subordinates) {
        this.subordinates = subordinates;
    }

    @Override
    public String toString() {
        String employees = this.subordinates
                .stream()
                .map(e -> e.toString())
                .map(e -> "        - " + e)
                .collect(Collectors.joining("\n"));

        return String.format("%s %s | Employees: %d%n%s%n",
                this.firstName, this.lastName,
                this.subordinates.size()
                ,employees);
    }
}
