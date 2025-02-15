package org.example.bookstoremanagementsystem.model.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "orderItems")
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "orderId", nullable = false)
    @NotNull(message = "Items should be assigned to an order")
    private Integer orderId;

    @Column(name = "bookId", nullable = false)
    @NotNull(message = "An order must contain a book")
    private Integer bookId;

    @Column(name = "quantity", nullable = false)
    @Min(value = 1, message = "An order contains at least one copy of a book")
    private Integer quantity;
}
