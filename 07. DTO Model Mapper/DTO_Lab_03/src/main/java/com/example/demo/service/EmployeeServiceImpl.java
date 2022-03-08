package com.example.demo.service;

import com.example.demo.EmployeeDto;
import com.example.demo.entities.Employee;
import com.example.demo.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public Employee findOneById(int id) {
        return this.employeeRepository.findById(id);
    }

    @Override
    public void save(Employee employee) {
        this.employeeRepository.save(employee);
    }

    @Override
    public List<EmployeeDto> findAllByBirthdayBeforeOrderBySalaryDesc(int year) {
        LocalDate date = LocalDate.of(year, 1, 1);
        return this.employeeRepository.findAllByBirthdayBeforeOrderBySalaryDesc(date);
    }

}
