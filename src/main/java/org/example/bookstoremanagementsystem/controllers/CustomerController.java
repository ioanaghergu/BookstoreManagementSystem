package org.example.bookstoremanagementsystem.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.bookstoremanagementsystem.dto.CustomerDTO;
import org.example.bookstoremanagementsystem.dto.OrderDTO;
import org.example.bookstoremanagementsystem.services.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customers")
@Tag(name = "Customer", description = "Endpoints for managing customers")
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerService customerService;

    @Operation(summary = "Get a customer by ID", description = "Retrieves detailes about a customer using the customer's ID")
    @GetMapping("/{id}")
    public ResponseEntity<CustomerDTO> getCustomerById(@PathVariable int id) {
        CustomerDTO customer = customerService.findCustomerById(id);
        return new ResponseEntity<>(customer, HttpStatus.OK);
    }

    @Operation(summary = "Get a list of all the customers", description = "Retrieves a list with all the customers from the database")
    @GetMapping
    public ResponseEntity<List<CustomerDTO>> getAllCustomers() {
        List<CustomerDTO> customers = customerService.getAllCustomers();
        return new ResponseEntity<>(customers, HttpStatus.OK);

    }

    @Operation(summary = "Get all orders for a customer", description = "Retrieves a list with all the orders places by a customer")
    @GetMapping("/orders")
    public ResponseEntity<List<OrderDTO>> getAllOrders(@RequestParam("Customer ID") int id) {
        List<OrderDTO> orders = customerService.getAllOrdersForACustomer(id);
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @Operation(summary = "Create new customer", description = "Adds a new customer to the database. Make sure the list reviews is empty, a customer hasn't left reviews to any books if it's only now being inserted")
    @PostMapping
    public ResponseEntity<CustomerDTO> createCustomer(@Valid @RequestBody CustomerDTO customerDTO) {
        CustomerDTO customer = customerService.createCustomer(customerDTO);
        return new ResponseEntity<>(customer, HttpStatus.CREATED);
    }

    @Operation(summary = "Update customer's detailes", description = "Updates the details of a customer registered into the system")
    @PutMapping("/{id}")
    public ResponseEntity<CustomerDTO> updateCustomer(@PathVariable int id, @Valid @RequestBody CustomerDTO customerDTO) {
        CustomerDTO customer = customerService.updateCustomer(id, customerDTO);
        return new ResponseEntity<>(customer, HttpStatus.OK);
    }

    @Operation(summary = "Delete a customer", description = "Deletes a customer by ID from the database")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable int id) {
        customerService.deleteCustomer(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
