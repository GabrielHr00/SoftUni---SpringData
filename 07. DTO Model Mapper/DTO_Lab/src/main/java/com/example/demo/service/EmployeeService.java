package com.example.demo.service;

import com.example.demo.demo.entities.Employee;

public interface EmployeeService {
    Employee findOneById(int id);
    void save(Employee employee);
}
