package org.example.bookstoremanagementsystem.services;

import org.example.bookstoremanagementsystem.dto.OrderDTO;
import org.example.bookstoremanagementsystem.mappers.OrderMapper;
import org.example.bookstoremanagementsystem.model.entities.Book;
import org.example.bookstoremanagementsystem.model.entities.Order;
import org.example.bookstoremanagementsystem.model.entities.OrderItem;
import org.example.bookstoremanagementsystem.model.entities.enums.OrderStatus;
import org.example.bookstoremanagementsystem.repositories.BookRepository;
import org.example.bookstoremanagementsystem.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private BookRepository bookRepository;

    public OrderDTO findOrderById(int id) {
        Order order = orderRepository.findById(id).orElseThrow(() -> new RuntimeException("Order with id " + id + " not found"));
        return OrderMapper.toOrderDTO(order);
    }

    public List<OrderDTO> getAllOrders() {
        List<Order> orders = orderRepository.findAll();
        return orders.stream().map(OrderMapper::toOrderDTO).collect(Collectors.toList());
    }

    public OrderDTO createOrder(OrderDTO orderDTO) {
        Order order = OrderMapper.toOrder(orderDTO);

        order.setStatus(OrderStatus.PENDING);
        List<OrderItem> items = order.getItems().stream().toList();

        float price = 0.0f;
        for(OrderItem item : items) {
            Book book = bookRepository.findById(item.getBookId()).orElse(null);
            if(book != null) {
                book.setStock(book.getStock() - item.getQuantity());
                price += item.getQuantity() * book.getPrice();
            }
        }
        order.setPrice(price);
        return OrderMapper.toOrderDTO(orderRepository.save(order));
    }

    public void deleteOrder(int id) {
        Order order = orderRepository.findById(id).orElseThrow(() -> new RuntimeException("Order with id " + id + " not found"));
        orderRepository.delete(order);
    }
}
