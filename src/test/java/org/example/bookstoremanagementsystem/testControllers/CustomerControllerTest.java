package org.example.bookstoremanagementsystem.testControllers;

import org.example.bookstoremanagementsystem.controllers.CustomerController;
import org.example.bookstoremanagementsystem.dto.CustomerDTO;
import org.example.bookstoremanagementsystem.dto.OrderDTO;
import org.example.bookstoremanagementsystem.mappers.CustomerMapper;
import org.example.bookstoremanagementsystem.mappers.OrderMapper;
import org.example.bookstoremanagementsystem.model.entities.Customer;
import org.example.bookstoremanagementsystem.model.entities.Order;
import org.example.bookstoremanagementsystem.services.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CustomerControllerTest {

    @Mock
    private CustomerService customerService;

    @InjectMocks
    private CustomerController customerController;

    private CustomerDTO customerDTO;
    private OrderDTO orderDTO;
    private Customer customer;
    private Order order;

    @BeforeEach
    void setUp() {
        order = Order.builder()
                .id(1)
                .customerId(1)
                .price(50.0f)
                .build();

        customer = Customer.builder()
                .id(1)
                .name("John Doe")
                .email("john.doe@example.com")
                .phone("1234567890")
                .address("123 Main St")
                .orders(Arrays.asList(order))
                .build();

        customerDTO = CustomerMapper.toCustomerDTO(customer);
        orderDTO = OrderMapper.toOrderDTO(order);
    }

    @Test
    @DisplayName("""
            Given there is a customer in the database with the requested ID
            Then details of the customer and status 200 are returned
            """)
    void test1() {
        when(customerService.findCustomerById(1)).thenReturn(customerDTO);

        ResponseEntity<CustomerDTO> response = customerController.getCustomerById(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(customerDTO, response.getBody());
        verify(customerService, times(1)).findCustomerById(1);
    }

    @Test
    @DisplayName("""
            Given there is a request to display all the customers from the database
            Then a list of all customers and status 200 are returned
            """)
    void test2() {
        List<CustomerDTO> customers = Arrays.asList(customerDTO);
        when(customerService.getAllCustomers()).thenReturn(customers);

        ResponseEntity<List<CustomerDTO>> response = customerController.getAllCustomers();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(customers, response.getBody());
        verify(customerService, times(1)).getAllCustomers();
    }

    @Test
    @DisplayName("""
            Given there is a request to display the orders assigned to a customer
            Then a list of all orders for that customer and status 200 are returned
            """)
    void test3() {
        List<OrderDTO> orders = Arrays.asList(orderDTO);
        when(customerService.getAllOrdersForACustomer(1)).thenReturn(orders);

        ResponseEntity<List<OrderDTO>> response = customerController.getAllOrders(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(orders, response.getBody());
        verify(customerService, times(1)).getAllOrdersForACustomer(1);
    }

    @Test
    @DisplayName("""
            Given there is a request to create a customer with valid details
            Then the created customer and status 201 are returned
            """)
    void test4() {
        when(customerService.createCustomer(customerDTO)).thenReturn(customerDTO);

        ResponseEntity<CustomerDTO> response = customerController.createCustomer(customerDTO);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(customerDTO, response.getBody());
        verify(customerService, times(1)).createCustomer(customerDTO);
    }

    @Test
    @DisplayName("""
            Given there is a valid request for updating a customer's details
            Then the customer is updated and status 200 is returned
            """)
    void test5() {
        when(customerService.updateCustomer(1, customerDTO)).thenReturn(customerDTO);

        ResponseEntity<CustomerDTO> response = customerController.updateCustomer(1, customerDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(customerDTO, response.getBody());
        verify(customerService, times(1)).updateCustomer(1, customerDTO);
    }

    @Test
    @DisplayName("""
            Given there is a request for deleting a customer and the customer exists in the database
            Then the customer is deleted and status 204 is returned
            """)
    void test6() {
        doNothing().when(customerService).deleteCustomer(1);

        ResponseEntity<Void> response = customerController.deleteCustomer(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(customerService, times(1)).deleteCustomer(1);
    }
}