package org.example.bookstoremanagementsystem.services;

import org.example.bookstoremanagementsystem.dto.EmployeeDTO;
import org.example.bookstoremanagementsystem.dto.OrderDTO;
import org.example.bookstoremanagementsystem.mappers.EmployeeMapper;
import org.example.bookstoremanagementsystem.mappers.OrderMapper;
import org.example.bookstoremanagementsystem.model.entities.Employee;
import org.example.bookstoremanagementsystem.model.entities.Order;
import org.example.bookstoremanagementsystem.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    public EmployeeDTO findEmployeeById(int id) {
        Employee employee = employeeRepository.findById(id).orElseThrow(() -> new RuntimeException("Employee with id " + id + " not found"));
        return EmployeeMapper.toEmployeeDTO(employee);
    }

    public List<EmployeeDTO> getAllEmployees() {
        List<Employee> employees = employeeRepository.findAll();
        return employees.stream().map(EmployeeMapper::toEmployeeDTO).collect(Collectors.toList());
    }

    public List<OrderDTO> getAllOrdersForAnEmployee(int id) {
        List<Order> orders = employeeRepository.getAllOrdersByEmployeeId(id);
        return orders.stream().map(OrderMapper::toOrderDTO).toList();
    }

    public EmployeeDTO createEmployee(EmployeeDTO employeeDTO) {
        Employee employee = EmployeeMapper.toEmployee(employeeDTO);
        return EmployeeMapper.toEmployeeDTO(employeeRepository.save(employee));
    }

    public EmployeeDTO updateEmployee(int id, EmployeeDTO employeeDTO) {
        Employee employee = employeeRepository.findById(id).orElseThrow(() -> new RuntimeException("Employee with id " + id + " not found"));

        if(employeeDTO.getName() != null)
            employee.setName(employeeDTO.getName());

        if(employeeDTO.getSalary() != null)
            employee.setSalary(employeeDTO.getSalary());

        Employee updatedEmployee = employeeRepository.save(employee);
        return EmployeeMapper.toEmployeeDTO(updatedEmployee);
    }

    public void deleteEmployee(int id) {
        Employee employee = employeeRepository.findById(id).orElseThrow(() -> new RuntimeException("Employee with id " + id + " not found"));
        employeeRepository.delete(employee);
    }
}
