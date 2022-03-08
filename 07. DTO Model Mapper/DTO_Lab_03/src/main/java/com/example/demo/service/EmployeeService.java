package com.example.demo.service;

import com.example.demo.EmployeeDto;
import com.example.demo.entities.Employee;

import java.time.LocalDate;
import java.util.List;

public interface EmployeeService {
    Employee findOneById(int id);
    void save(Employee employee);
    List<EmployeeDto> findAllByBirthdayBeforeOrderBySalaryDesc(int year);
}
