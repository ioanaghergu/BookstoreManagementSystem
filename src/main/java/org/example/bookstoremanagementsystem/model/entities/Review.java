package org.example.bookstoremanagementsystem.model.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
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
@Table(name = "reviews")
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "rating", nullable = false)
    @NotNull(message = "A review of a book requires the existence of a rating")
    @Min(value = 0, message = "Rating of a book should be at least 0 stars")
    @Max(value = 5, message = "Rating of a book can't be more than 5 stars")
    private Integer rating;

    @Column(name = "comment")
    private String comment;

    @Column(name = "bookId", nullable = false)
    @NotNull(message = "A review is required to be left by a customer")
    private Integer bookId;

    @Column(name = "customerId", nullable = false)
    @NotNull(message = "A review is required to be left by a customer")
    private Integer customerId;
}
