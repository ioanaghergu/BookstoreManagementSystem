package org.example.bookstoremanagementsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.bookstoremanagementsystem.model.entities.enums.OrderStatus;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {
    private Integer id;
    private Integer customerId;
    private Integer employeeId;
    private Float price;
    private OrderStatus status;
    private LocalDate orderDate;
    private List<OrderItemDTO> items;
}
