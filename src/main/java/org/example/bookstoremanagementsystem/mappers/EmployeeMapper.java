package org.example.bookstoremanagementsystem.mappers;

import org.example.bookstoremanagementsystem.dto.EmployeeDTO;
import org.example.bookstoremanagementsystem.model.entities.Employee;

import java.util.Collections;
import java.util.stream.Collectors;

public class EmployeeMapper {
    public static EmployeeDTO toEmployeeDTO(Employee employee) {
        if(employee == null)
            return null;

        return EmployeeDTO.builder()
                .id(employee.getId())
                .name(employee.getName())
                .salary(employee.getSalary())
                .orders(employee.getOrders() != null
                        ? employee.getOrders()
                            .stream()
                            .map(OrderMapper::toOrderDTO)
                            .collect(Collectors.toList())
                        : Collections.emptyList())
                .build();
    }

    public static Employee toEmployee(EmployeeDTO employeeDTO) {
        if(employeeDTO == null)
            return null;

        return Employee.builder()
                .id(employeeDTO.getId())
                .name(employeeDTO.getName())
                .salary(employeeDTO.getSalary())
                .orders(employeeDTO.getOrders() != null
                        ? employeeDTO.getOrders()
                            .stream()
                            .map(OrderMapper::toOrder)
                            .collect(Collectors.toList())
                        : Collections.emptyList())
                .build();
    }
}
