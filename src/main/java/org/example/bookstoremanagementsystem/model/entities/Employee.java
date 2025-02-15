package org.example.bookstoremanagementsystem.model.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "employees")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name", nullable = false)
    @NotNull(message = "Employee name is required")
    @Pattern(regexp = "^[A-Z][a-z]*([\\s][A-Z][a-z]*)*$", message = "The first letter of the name should be capital")
    private String name;

    @Column(name = "salary", nullable = false)
    @NotNull(message = "Employee salary is required")
    @Min(value = 2500, message = "The salary must have a value of at least 2500 lei")
    private Float salary;

    @OneToMany(mappedBy = "employeeId", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Order> orders;

}
