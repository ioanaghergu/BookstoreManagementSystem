package org.example.bookstoremanagementsystem.testServices;

import org.example.bookstoremanagementsystem.dto.OrderDTO;
import org.example.bookstoremanagementsystem.mappers.OrderMapper;
import org.example.bookstoremanagementsystem.model.entities.Book;
import org.example.bookstoremanagementsystem.model.entities.Order;
import org.example.bookstoremanagementsystem.model.entities.OrderItem;
import org.example.bookstoremanagementsystem.model.entities.enums.OrderStatus;
import org.example.bookstoremanagementsystem.repositories.BookRepository;
import org.example.bookstoremanagementsystem.repositories.OrderRepository;
import org.example.bookstoremanagementsystem.services.OrderService;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private OrderService orderService;

    private Order order;
    private OrderDTO orderDTO;
    private Book book;
    private OrderItem orderItem;

    @BeforeEach
    public void setUp() {
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
            Given there is an order with the given ID in the database
            Then the order details are returned
            """)
    public void test1() {
        when(orderRepository.findById(1)).thenReturn(java.util.Optional.of(order));

        OrderDTO result = orderService.findOrderById(1);

        assertNotNull(result);
        assertEquals(order.getId(), result.getId());
        assertEquals(order.getPrice(), result.getPrice());
        assertEquals(order.getStatus(), result.getStatus());
        assertEquals(order.getItems().size(), result.getItems().size());

        verify(orderRepository, times(1)).findById(1);
    }

    @Test
    @DisplayName("""
            Given there are multiple orders in the database
            Then a list of all orders is returned
            """)
    public void test2() {
        when(orderRepository.findAll()).thenReturn(Arrays.asList(order));

        List<OrderDTO> result = orderService.getAllOrders();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(order.getId(), result.getFirst().getId());
        assertEquals(order.getPrice(), result.getFirst().getPrice());

        verify(orderRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("""
            Given details of an order
            Then the order price is computed and the order is inserted into the database
            """)
    public void test3() {
        when(bookRepository.findById(101)).thenReturn(java.util.Optional.of(book));
        when(orderRepository.save(any(Order.class))).thenReturn(order);

        OrderDTO result = orderService.createOrder(orderDTO);

        assertNotNull(result);
        assertEquals(order.getId(), result.getId());
        assertEquals(order.getPrice(), result.getPrice());
        assertEquals(order.getItems().size(), result.getItems().size());

        verify(bookRepository, times(1)).findById(101);
        verify(orderRepository, times(1)).save(any(Order.class));
    }

    @Test
    @DisplayName("""
            Given there is an order with the given ID in the database
            Then the order is deleted
            """)
    public void test4() {
        when(orderRepository.findById(1)).thenReturn(java.util.Optional.of(order));
        doNothing().when(orderRepository).delete(order);

        orderService.deleteOrder(1);

        verify(orderRepository, times(1)).findById(1);
        verify(orderRepository, times(1)).delete(order);
    }
}
