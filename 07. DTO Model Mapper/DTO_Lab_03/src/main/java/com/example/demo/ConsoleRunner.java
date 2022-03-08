package com.example.demo;

import com.example.demo.entities.Employee;
import com.example.demo.service.EmployeeService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;

@Component
public class ConsoleRunner implements CommandLineRunner {
    @Autowired
    private EmployeeService employeeService;

    @Autowired
    public ConsoleRunner(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @Override
    public void run(String... args) throws Exception {
        persist();

        this.employeeService.findAllByBirthdayBeforeOrderBySalaryDesc(2023).forEach(System.out::println);

    }

    private void persist() {
        Employee manager = new Employee("Mr.", "Manager",
                BigDecimal.TEN, LocalDate.now(), null);

        this.employeeService.save(manager);


        Employee empl1 = new Employee("Mr.", "Employee 1",
                BigDecimal.ONE, LocalDate.now(), manager);

        this.employeeService.save(empl1);
    }
}
