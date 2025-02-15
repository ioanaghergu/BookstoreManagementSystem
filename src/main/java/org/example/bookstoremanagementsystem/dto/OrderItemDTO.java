package org.example.bookstoremanagementsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemDTO {
    private Integer id;
    private Integer orderId;
    private Integer bookId;
    private Integer quantity;
}
