package org.example.bookstoremanagementsystem.model.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
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

    @ManyToOne
    @JoinColumn(name = "customerId", nullable = false)
    @NotNull(message = "An order must be places by a customer")
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "employeeId", nullable = false)
    @NotNull(message = "An employee should be assigned to process an order")
    private Employee employee;

    @Column(name = "price", nullable = false)
    @NotNull(message = "The total price of an order can't be 0")
    private Float price;

    @Column(name = "status", nullable = false)
    @NotNull(message = "Order status is required")
    @Enumerated(EnumType.STRING)
    private String status;

    @Column(name = "orderDate", nullable = false)
    @NotNull(message = "Ordering date is required")
    @PastOrPresent(message = "Ordering date should be valid")
    private LocalDate orderDate;

    @OneToMany(mappedBy = "order")
    private List<OrderItem> items = new ArrayList<>();


}
