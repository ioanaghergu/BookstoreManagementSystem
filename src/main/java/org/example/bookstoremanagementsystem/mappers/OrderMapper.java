package org.example.bookstoremanagementsystem.mappers;

import org.example.bookstoremanagementsystem.dto.OrderDTO;
import org.example.bookstoremanagementsystem.model.entities.Order;

import java.util.Collections;
import java.util.stream.Collectors;

public class OrderMapper {
    public static OrderDTO toOrderDTO(Order order) {
        if(order == null)
            return null;

        return OrderDTO.builder()
                .id(order.getId())
                .customerId(order.getCustomerId())
                .employeeId(order.getEmployeeId())
                .price(order.getPrice())
                .status(order.getStatus())
                .orderDate(order.getOrderDate())
                .items(order.getItems() != null
                        ? order.getItems()
                            .stream()
                            .map(OrderItemMapper::toOrderItemDTO)
                            .collect(Collectors.toList())
                        : Collections.emptyList())
                .build();
    }

    public static Order toOrder(OrderDTO orderDTO) {
        if(orderDTO == null)
            return null;

        return Order.builder()
                .id(orderDTO.getId())
                .customerId(orderDTO.getCustomerId())
                .employeeId(orderDTO.getEmployeeId())
                .price(orderDTO.getPrice())
                .status(orderDTO.getStatus())
                .orderDate(orderDTO.getOrderDate())
                .items(orderDTO.getItems() != null
                        ? orderDTO.getItems()
                            .stream()
                            .map(OrderItemMapper::toOrderItem)
                            .collect(Collectors.toList())
                        : Collections.emptyList())
                .build();

    }
}
