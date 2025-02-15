package org.example.bookstoremanagementsystem.model.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
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
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    @Column(name = "customerId", nullable = false)
    @NotNull(message = "An order must be places by a customer")
    private Integer customerId;

    @Column(name = "employeeId", nullable = false)
    @NotNull(message = "An employee should be assigned to process an order")
    private Integer employeeId;

    @Column(name = "price", nullable = false)
    @NotNull(message = "The total price of an order can't be 0")
    private Float price;

    @Column(name = "status", nullable = false)
    @NotNull(message = "Order status is required")
    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @Column(name = "orderDate", nullable = false)
    @NotNull(message = "Ordering date is required")
    @PastOrPresent(message = "Ordering date should be valid")
    private LocalDate orderDate;

    @OneToMany(mappedBy = "orderId", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<OrderItem> items;

}
