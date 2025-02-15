package org.example.bookstoremanagementsystem.model.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;


import java.util.List;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="books")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "title", nullable = false)
    @NotNull(message = "Book title is required")
    @Size(min = 3, message = "The title of a book must consist of at least 3 letters")
    private String title;

    @Column(name = "author", nullable = false)
    @NotNull(message = "Book author is required")
    @Pattern(regexp = "^[A-Z][a-z]*([\\s][A-Z][a-z]*)*$", message = "The first letter of the author's name should be capital")
    private String author;

    @Column(name = "genre", nullable = false)
    @NotNull(message = "Book genre is required")
    private String genre;

    @Column(name = "description", nullable = false)
    @NotNull(message = "Book description is required")
    private String description;

    @Column(name = "price", nullable = false)
    @NotNull(message = "Book price is required")
    private Float price;

    @Column(name = "avg_rating")
    private Float avgRating;

    @Column(name = "stock")
    private Integer stock;

    @OneToMany(mappedBy = "bookId", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Review> reviews;
}
