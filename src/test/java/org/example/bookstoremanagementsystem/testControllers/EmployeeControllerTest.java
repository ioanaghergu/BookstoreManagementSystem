package org.example.bookstoremanagementsystem.testControllers;

import org.example.bookstoremanagementsystem.controllers.EmployeeController;
import org.example.bookstoremanagementsystem.dto.EmployeeDTO;
import org.example.bookstoremanagementsystem.dto.OrderDTO;
import org.example.bookstoremanagementsystem.mappers.EmployeeMapper;
import org.example.bookstoremanagementsystem.mappers.OrderMapper;
import org.example.bookstoremanagementsystem.model.entities.Employee;
import org.example.bookstoremanagementsystem.model.entities.Order;
import org.example.bookstoremanagementsystem.model.entities.OrderItem;
import org.example.bookstoremanagementsystem.model.entities.enums.OrderStatus;
import org.example.bookstoremanagementsystem.services.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EmployeeControllerTest {

    @Mock
    private EmployeeService employeeService;

    @InjectMocks
    private EmployeeController employeeController;

    private EmployeeDTO employeeDTO;
    private Employee employee;
    private Order order;
    private OrderItem orderItem;

    @BeforeEach
    void setUp() {
        orderItem = OrderItem.builder()
                .id(1)
                .orderId(1)
                .bookId(101)
                .quantity(2)
                .build();

        order = Order.builder()
                .id(1)
                .customerId(1)
                .employeeId(1)
                .price(25.50f)
                .status(OrderStatus.PENDING)
                .orderDate(LocalDate.now())
                .items(Arrays.asList(orderItem))
                .build();

        employee = Employee.builder()
                .id(1)
                .name("John Doe")
                .salary(3000f)
                .orders(Arrays.asList(order))
                .build();

        employeeDTO = EmployeeMapper.toEmployeeDTO(employee);
    }

    @Test
    @DisplayName("""
            Given there is an employee with the requested ID in the database
            Then details of the employee and status 200 are returned
            """)
    void test1() {
        when(employeeService.findEmployeeById(1)).thenReturn(employeeDTO);

        ResponseEntity<EmployeeDTO> response = employeeController.getEmployeeById(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(employeeDTO, response.getBody());
        verify(employeeService, times(1)).findEmployeeById(1);
    }

    @Test
    @DisplayName("""
            Given there is a request to display all the books in the database
            Then a list with the books and status 200 are returned
            """)
    void test2() {
        List<EmployeeDTO> employees = Arrays.asList(employeeDTO);
        when(employeeService.getAllEmployees()).thenReturn(employees);

        ResponseEntity<List<EmployeeDTO>> response = employeeController.getAllEmployees();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(employees, response.getBody());
        verify(employeeService, times(1)).getAllEmployees();
    }

    @Test
    @DisplayName("""
            Given there is a request to display the orders assigned for processing to an employee
            Then a list of all orders for that employee and status 200 are returned
            """)
    void test3() {
        List<OrderDTO> orders = Arrays.asList(OrderMapper.toOrderDTO(order));
        when(employeeService.getAllOrdersForAnEmployee(1)).thenReturn(orders);

        ResponseEntity<List<OrderDTO>> response = employeeController.getAllOrders(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(orders, response.getBody());
        verify(employeeService, times(1)).getAllOrdersForAnEmployee(1);
    }

    @Test
    @DisplayName("""
            Given there is a request to create an employee with valid details
            Then the created employee and status 201 are returned
            """)
    void test4() {
        when(employeeService.createEmployee(employeeDTO)).thenReturn(employeeDTO);

        ResponseEntity<EmployeeDTO> response = employeeController.createEmployee(employeeDTO);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(employeeDTO, response.getBody());
        verify(employeeService, times(1)).createEmployee(employeeDTO);
    }

    @Test
    @DisplayName("""
            Given there is a valid request for updating an employee's details
            Then the employee is updated and status 200 is returned
            """)
    void test5() {
        when(employeeService.updateEmployee(1, employeeDTO)).thenReturn(employeeDTO);

        ResponseEntity<EmployeeDTO> response = employeeController.updateEmployee(1, employeeDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(employeeDTO, response.getBody());
        verify(employeeService, times(1)).updateEmployee(1, employeeDTO);
    }

    @Test
    @DisplayName("""
            Given there is a request for deleting an employee and the employee exists in the database
            Then the employee is deleted and status 204 is returned
            """)
    void test6() {
        doNothing().when(employeeService).deleteEmployee(1);

        ResponseEntity<Void> response = employeeController.deleteEmployee(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(employeeService, times(1)).deleteEmployee(1);
    }
}