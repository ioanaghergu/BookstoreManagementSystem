package org.example.bookstoremanagementsystem.testControllers;

import org.example.bookstoremanagementsystem.controllers.OrderController;
import org.example.bookstoremanagementsystem.dto.OrderDTO;
import org.example.bookstoremanagementsystem.mappers.OrderMapper;
import org.example.bookstoremanagementsystem.model.entities.Book;
import org.example.bookstoremanagementsystem.model.entities.Order;
import org.example.bookstoremanagementsystem.model.entities.OrderItem;
import org.example.bookstoremanagementsystem.model.entities.enums.OrderStatus;
import org.example.bookstoremanagementsystem.services.OrderService;
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
public class OrderControllerTest {

    @Mock
    private OrderService orderService;

    @InjectMocks
    private OrderController orderController;

    private OrderDTO orderDTO;
    private Order order;
    private OrderItem orderItem;
    private Book book;

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
                .price(50.0f)
                .status(OrderStatus.PENDING)
                .orderDate(LocalDate.now())
                .items(Arrays.asList(orderItem))
                .build();

        orderDTO = OrderMapper.toOrderDTO(order);

        book = Book.builder()
                .id(101)
                .title("Test Book")
                .price(25.0f)
                .stock(10)
                .build();
    }

    @Test
    @DisplayName("""
            Given there is an order in the database with the requested ID
            Then details of the order and status 200 are returned
            """)
    void test1() {
        when(orderService.findOrderById(1)).thenReturn(orderDTO);

        ResponseEntity<OrderDTO> response = orderController.getOrderById(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(orderDTO, response.getBody());
        verify(orderService, times(1)).findOrderById(1);
    }

    @Test
    @DisplayName("""
            Given there is a request to display all the orders from the database
            Then a list of all orders and status 200 are returned
            """)
    void test2() {
        List<OrderDTO> orders = Arrays.asList(orderDTO);
        when(orderService.getAllOrders()).thenReturn(orders);

        ResponseEntity<List<OrderDTO>> response = orderController.getAllOrders();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(orders, response.getBody());
        verify(orderService, times(1)).getAllOrders();
    }

    @Test
    @DisplayName("""
            Given there is a request to create an order with valid details
            Then the created order and status 201 are returned
            """)
    void test3() {
        when(orderService.createOrder(orderDTO)).thenReturn(orderDTO);

        ResponseEntity<OrderDTO> response = orderController.createOrder(orderDTO);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(orderDTO, response.getBody());
        verify(orderService, times(1)).createOrder(orderDTO);
    }

    @Test
    @DisplayName("""
            Given there is a request for deleting an order that exists in the database
            Then the order is deleted and status 204 is returned
            """)
    void test4() {
        doNothing().when(orderService).deleteOrder(1);

        ResponseEntity<Void> response = orderController.deleteOrder(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(orderService, times(1)).deleteOrder(1);
    }
}