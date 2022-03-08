package com.example.demo.repositories;

import com.example.demo.EmployeeDto;
import com.example.demo.entities.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
    Employee findById(int id);
    Employee save(Employee employee);
    List<EmployeeDto> findAllByBirthdayBeforeOrderBySalaryDesc(LocalDate date);
}
