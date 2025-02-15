package org.example.bookstoremanagementsystem.model.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.bookstoremanagementsystem.validator.ValidEmail;
import org.example.bookstoremanagementsystem.validator.ValidPhoneNumber;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "customers")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name", nullable = false)
    @NotNull(message = "Customer name is required")
    @Pattern(regexp = "^[A-Z][a-z]*([\\s][A-Z][a-z]*)*$", message = "The first letter of the customer's name should be capital")
    private String name;

    @Column(name = "email", nullable = false, unique = true)
    @NotNull(message = "Customer email is required")
    @ValidEmail
    private String email;

    @Column(name = "phone", nullable = false, unique = true)
    @NotNull(message = "Customer phone number is required")
    @ValidPhoneNumber
    private String phone;

    @Column(name = "address")
    private String address;

    @OneToMany(mappedBy = "customerId", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Order> orders;

    @OneToMany(mappedBy = "customerId", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Review> reviews;

}
