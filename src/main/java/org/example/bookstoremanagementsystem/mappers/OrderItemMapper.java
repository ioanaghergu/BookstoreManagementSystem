package org.example.bookstoremanagementsystem.mappers;

import org.example.bookstoremanagementsystem.dto.OrderItemDTO;
import org.example.bookstoremanagementsystem.model.entities.OrderItem;

public class OrderItemMapper {
    public static OrderItemDTO toOrderItemDTO(OrderItem orderItem) {
        if(orderItem == null)
            return null;

        return OrderItemDTO.builder()
                .id(orderItem.getId())
                .quantity(orderItem.getQuantity())
                .orderId(orderItem.getOrderId())
                .bookId(orderItem.getBookId())
                .build();
    }

    public static OrderItem toOrderItem(OrderItemDTO orderItemDTO) {
        if(orderItemDTO == null)
            return null;

        return OrderItem.builder()
                .id(orderItemDTO.getId())
                .quantity(orderItemDTO.getQuantity())
                .orderId(orderItemDTO.getOrderId())
                .bookId(orderItemDTO.getBookId())
                .build();
    }
}
