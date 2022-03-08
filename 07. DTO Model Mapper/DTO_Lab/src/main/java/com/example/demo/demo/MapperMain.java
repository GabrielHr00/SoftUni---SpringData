package com.example.demo.demo;

import com.example.demo.demo.ManagerDto;
import com.example.demo.demo.entities.Address;
import com.example.demo.demo.entities.Employee;
import org.modelmapper.ModelMapper;

import java.math.BigDecimal;
import java.time.LocalDate;

public class MapperMain {
    public static void main(String[] args) {
        ModelMapper mapper = new ModelMapper();
        Address address = new Address("boris 3", 3, "Sofia",
                "Bulgaria");

        Employee manager = new Employee("Mr.", "Manager",
                BigDecimal.TEN, LocalDate.now(), address, true);


        Employee empl1 = new Employee("Mr.", "Employee 1",
                BigDecimal.ONE, LocalDate.now(), address, true);


        Employee empl2 = new Employee("Mr.", "Employee 2",
                BigDecimal.ZERO, LocalDate.now(), address, true);

        manager.addEmployee(empl1);
        manager.addEmployee(empl2);

        ManagerDto dto = mapper.map(manager, ManagerDto.class);
        System.out.println(dto);

    }
}
