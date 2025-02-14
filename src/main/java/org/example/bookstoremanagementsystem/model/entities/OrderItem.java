package org.example.bookstoremanagementsystem.model.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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

    @ManyToOne
    @JoinColumn(name = "orderId", nullable = false)
    @NotNull(message = "Items should be assigned to an order")
    private Order order;

    @ManyToOne
    @JoinColumn(name = "bookId", nullable = false)
    @NotNull(message = "")
    private Book book;

    @Column(name = "quantity", nullable = false)
    @Min(value = 1, message = "An order contains at least one copy of a book")
    private Integer quantity;
}
