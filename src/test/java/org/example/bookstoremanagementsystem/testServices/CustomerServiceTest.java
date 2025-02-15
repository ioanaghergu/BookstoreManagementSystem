package org.example.bookstoremanagementsystem.testServices;

import org.example.bookstoremanagementsystem.dto.CustomerDTO;
import org.example.bookstoremanagementsystem.dto.OrderDTO;
import org.example.bookstoremanagementsystem.mappers.CustomerMapper;
import org.example.bookstoremanagementsystem.mappers.OrderMapper;
import org.example.bookstoremanagementsystem.model.entities.Customer;
import org.example.bookstoremanagementsystem.model.entities.Order;
import org.example.bookstoremanagementsystem.repositories.CustomerRepository;
import org.example.bookstoremanagementsystem.services.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerService customerService;

    private Customer customer;
    private CustomerDTO customerDTO;
    private Order order;
    private OrderDTO orderDTO;

    @BeforeEach
    public void setUp() {
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
            Given there is a customer with the given ID in the database
            Then details of that customer are returned
            """)
    public void test1() {
        when(customerRepository.findById(1)).thenReturn(Optional.of(customer));

        CustomerDTO result = customerService.findCustomerById(1);

        assertNotNull(result);
        assertEquals(customer.getId(), result.getId());
        assertEquals(customer.getName(), result.getName());
        assertEquals(customer.getEmail(), result.getEmail());
        assertEquals(customer.getPhone(), result.getPhone());
        assertEquals(customer.getAddress(), result.getAddress());

        verify(customerRepository, times(1)).findById(1);
    }

    @Test
    @DisplayName("""
            Given there isn't a customer with the given ID in the databse
            Then an exception is thrown
            """)
    public void test2() {
        when(customerRepository.findById(999)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> customerService.findCustomerById(999));
        verify(customerRepository, times(1)).findById(999);
    }

    @Test
    @DisplayName("""
            Given there are multiple entries of customers in the database
            Then a list with all is returned
            """)
    public void test3() {
        when(customerRepository.findAll()).thenReturn(Arrays.asList(customer));

        List<CustomerDTO> result = customerService.getAllCustomers();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(customer.getId(), result.getFirst().getId());

        verify(customerRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("""
            Given there is a customer with the given ID in the database
            Then a list with the customer's orders is returned
            """)
    public void test4() {
        when(customerRepository.getAllOrdersByCustomerId(1)).thenReturn(Arrays.asList(order));

        List<OrderDTO> result = customerService.getAllOrdersForACustomer(1);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(order.getId(), result.getFirst().getId());

        verify(customerRepository, times(1)).getAllOrdersByCustomerId(1);
    }

    @Test
    @DisplayName("""
            Given a customer's details
            Then the customer is inserted in the database
            """)
    public void test5() {
        when(customerRepository.save(any(Customer.class))).thenReturn(customer);

        CustomerDTO result = customerService.createCustomer(customerDTO);

        assertNotNull(result);
        assertEquals(customer.getId(), result.getId());

        verify(customerRepository, times(1)).save(any(Customer.class));
    }

    @Test
    @DisplayName("""
            Given there is a customer with the given ID in the database
            Then that customer's details are updated and changes are saved in the database
            """)
    public void test6() {
        when(customerRepository.findById(1)).thenReturn(Optional.of(customer));
        when(customerRepository.save(any(Customer.class))).thenReturn(customer);

        customerDTO.setName("Updated Name");
        CustomerDTO result = customerService.updateCustomer(1, customerDTO);

        assertNotNull(result);
        assertEquals("Updated Name", result.getName());
        verify(customerRepository, times(1)).findById(1);
        verify(customerRepository, times(1)).save(any(Customer.class));
    }

    @Test
    @DisplayName("""
            Given there is a customer with the given ID in the database
            Then the customer is deleted
            """)
    public void test7() {
        when(customerRepository.findById(1)).thenReturn(Optional.of(customer));
        doNothing().when(customerRepository).delete(customer);

        customerService.deleteCustomer(1);

        verify(customerRepository, times(1)).findById(1);
        verify(customerRepository, times(1)).delete(customer);
    }
}
