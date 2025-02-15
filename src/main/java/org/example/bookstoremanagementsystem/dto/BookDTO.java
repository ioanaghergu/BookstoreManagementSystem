package org.example.bookstoremanagementsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BookDTO {
    private Integer id;
    private String title;
    private String author;
    private String genre;
    private String description;
    private Float price;
    private Float avgRating;
    private Integer stock;
    private List<ReviewDTO> reviews;
}
