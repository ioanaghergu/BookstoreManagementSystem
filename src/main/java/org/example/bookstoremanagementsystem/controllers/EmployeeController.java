package org.example.bookstoremanagementsystem.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.bookstoremanagementsystem.dto.EmployeeDTO;
import org.example.bookstoremanagementsystem.dto.OrderDTO;
import org.example.bookstoremanagementsystem.services.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employees")
@Tag(name = "Employee", description = "Endpoints for managing employees")
@RequiredArgsConstructor
public class EmployeeController {
    private final EmployeeService employeeService;

    @Operation(summary = "Get an employee by ID", description = "Retrieves details about an employee using the employee's ID")
    @GetMapping("/{id}")
    public ResponseEntity<EmployeeDTO> getEmployeeById(@PathVariable int id) {
        EmployeeDTO employee = employeeService.findEmployeeById(id);
        return new ResponseEntity<>(employee, HttpStatus.OK);
    }

    @Operation(summary = "Get a list of all the employees", description = "Retrieves a list with all the employees from the database")
    @GetMapping
    public ResponseEntity<List<EmployeeDTO>> getAllEmployees() {
        List<EmployeeDTO> employees = employeeService.getAllEmployees();
        return new ResponseEntity<>(employees, HttpStatus.OK);

    }

    @Operation(summary = "Get all orders for an employee", description = "Retrieves a list with all the orders assigned to an employee")
    @GetMapping("/orders")
    public ResponseEntity<List<OrderDTO>> getAllOrders(@RequestParam("Employee ID") int id) {
        List<OrderDTO> orders = employeeService.getAllOrdersForAnEmployee(id);
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @Operation(summary = "Create new employee", description = "Adds a new employee to the database. Make sure the list orders is empty, an employee hasn't been assigned to process any orders it's only now being created")
    @PostMapping
    public ResponseEntity<EmployeeDTO> createEmployee( @Valid @RequestBody EmployeeDTO employeeDTO) {
        EmployeeDTO employee = employeeService.createEmployee(employeeDTO);
        return new ResponseEntity<>(employee, HttpStatus.CREATED);
    }

    @Operation(summary = "Update employee's detailes", description = "Updates the details of an employee registered into the system")
    @PutMapping("/{id}")
    public ResponseEntity<EmployeeDTO> updateEmployee(@PathVariable int id, @Valid @RequestBody EmployeeDTO employeeDTO) {
        EmployeeDTO employee = employeeService.updateEmployee(id, employeeDTO);
        return new ResponseEntity<>(employee, HttpStatus.OK);
    }

    @Operation(summary = "Delete an employee", description = "Deletes an employee by ID from the database")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable int id) {
        employeeService.deleteEmployee(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
