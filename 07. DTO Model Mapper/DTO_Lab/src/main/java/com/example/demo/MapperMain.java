package com.example.demo;

import com.example.demo.entities.Address;
import com.example.demo.entities.Employee;
import org.modelmapper.ModelMapper;

import java.math.BigDecimal;
import java.time.LocalDate;

public class MapperMain {
    public static void main(String[] args) {
        ModelMapper mapper = new ModelMapper();
        Address address = new Address("boris 3", 3, "Sofia",
                "Bulgaria");

        Employee employee = new Employee("first", "last",
                BigDecimal.TEN, LocalDate.now(), address);

        EmployeeDto dto = mapper.map(employee, EmployeeDto.class);
        System.out.println(dto);

    }
}
