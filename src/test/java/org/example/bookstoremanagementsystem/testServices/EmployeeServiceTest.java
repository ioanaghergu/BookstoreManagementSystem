package org.example.bookstoremanagementsystem.testServices;

import org.example.bookstoremanagementsystem.dto.EmployeeDTO;
import org.example.bookstoremanagementsystem.dto.OrderDTO;
import org.example.bookstoremanagementsystem.model.entities.Employee;
import org.example.bookstoremanagementsystem.model.entities.Order;
import org.example.bookstoremanagementsystem.model.entities.OrderItem;
import org.example.bookstoremanagementsystem.model.entities.enums.OrderStatus;
import org.example.bookstoremanagementsystem.repositories.EmployeeRepository;
import org.example.bookstoremanagementsystem.services.EmployeeService;
import org.example.bookstoremanagementsystem.mappers.EmployeeMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeService employeeService;

    private Employee employee;
    private EmployeeDTO employeeDTO;
    private Order order;

    @BeforeEach
    public void setUp() {
        OrderItem orderItem = OrderItem.builder()
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
            Given there is an employee with the given ID in the database
            Then the employee details are returned
            """)
    public void test1() {
        when(employeeRepository.findById(1)).thenReturn(Optional.of(employee));

        EmployeeDTO result = employeeService.findEmployeeById(1);

        assertNotNull(result);
        assertEquals(employee.getId(), result.getId());
        assertEquals(employee.getName(), result.getName());
        assertEquals(employee.getSalary(), result.getSalary());
        assertEquals(employee.getOrders().size(), result.getOrders().size());

        verify(employeeRepository, times(1)).findById(1);
    }

    @Test
    @DisplayName("""
            Given there isn't an employee with the given ID in the database
            Then an exception is thrown
            """)
    public void test2() {
        when(employeeRepository.findById(999)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> employeeService.findEmployeeById(999));

        verify(employeeRepository, times(1)).findById(999);
    }

    @Test
    @DisplayName("""
            Given there are multiple employees in the database
            Then a list with all employees is returned
            """)
    public void test3() {
        when(employeeRepository.findAll()).thenReturn(Arrays.asList(employee));

        List<EmployeeDTO> result = employeeService.getAllEmployees();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(employee.getId(), result.getFirst().getId());
        assertEquals(employee.getName(), result.getFirst().getName());
        assertEquals(employee.getSalary(), result.getFirst().getSalary());

        verify(employeeRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("""
            Given there is an employee with the given ID in the database
            Then a list of orders the employee is assigned to proccess is returned for that employee
            """)
    public void test4() {
        when(employeeRepository.getAllOrdersByEmployeeId(1)).thenReturn(Arrays.asList(order));

        List<OrderDTO> result = employeeService.getAllOrdersForAnEmployee(1);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(order.getId(), result.getFirst().getId());
        assertEquals(order.getPrice(), result.getFirst().getPrice());
        assertEquals(order.getStatus(), result.getFirst().getStatus());

        assertEquals(1, result.getFirst().getItems().size());
        assertEquals(101, result.getFirst().getItems().getFirst().getBookId());  // Verificăm ID-ul cărții
        assertEquals(2, result.getFirst().getItems().getFirst().getQuantity());  // Verificăm cantitatea

        verify(employeeRepository, times(1)).getAllOrdersByEmployeeId(1);
    }

    @Test
    @DisplayName("""
            Given details of an employee
            Then the employee is inserted in the database
            """)
    public void test5() {
        when(employeeRepository.save(any(Employee.class))).thenReturn(employee);

        EmployeeDTO result = employeeService.createEmployee(employeeDTO);

        assertNotNull(result);
        assertEquals(employee.getId(), result.getId());
        assertEquals(employee.getName(), result.getName());
        assertEquals(employee.getSalary(), result.getSalary());

        verify(employeeRepository, times(1)).save(any(Employee.class));
    }

    @Test
    @DisplayName("""
            Given there is an employee with the given ID in the database
            Then the employee's details are updated and saved in the database
            """)
    public void test6() {
        when(employeeRepository.findById(1)).thenReturn(Optional.of(employee));
        when(employeeRepository.save(any(Employee.class))).thenReturn(employee);

        employeeDTO.setName("Updated Name");
        employeeDTO.setSalary(3500f);

        EmployeeDTO result = employeeService.updateEmployee(1, employeeDTO);

        assertNotNull(result);
        assertEquals("Updated Name", result.getName());
        assertEquals(3500f, result.getSalary());

        verify(employeeRepository, times(1)).findById(1);
        verify(employeeRepository, times(1)).save(any(Employee.class));
    }

    @Test
    @DisplayName("""
            Given there is an employee with the given ID in the database
            Then the employee is deleted from the database
            """)
    public void test7() {
        when(employeeRepository.findById(1)).thenReturn(Optional.of(employee));
        doNothing().when(employeeRepository).delete(employee);

        employeeService.deleteEmployee(1);

        verify(employeeRepository, times(1)).findById(1);
        verify(employeeRepository, times(1)).delete(employee);
    }
}
