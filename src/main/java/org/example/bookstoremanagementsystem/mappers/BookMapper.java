package org.example.bookstoremanagementsystem.mappers;

import org.example.bookstoremanagementsystem.dto.BookDTO;
import org.example.bookstoremanagementsystem.model.entities.Book;


import java.util.Collections;
import java.util.stream.Collectors;

public class BookMapper {

    public static BookDTO toBookDTO(Book book) {

        if (book == null)
            return null;

        return BookDTO.builder()
                .id(book.getId())
                .title(book.getTitle())
                .author(book.getAuthor())
                .genre(book.getGenre())
                .description(book.getDescription())
                .price(book.getPrice())
                .stock(book.getStock())
                .avgRating(book.getAvgRating())
                .reviews(book.getReviews() != null
                            ? book.getReviews()
                                .stream()
                                .map(ReviewMapper::toReviewDTO)
                                .collect(Collectors.toList())
                            : Collections.emptyList())
                .build();
    }

    public static Book toBook(BookDTO bookDTO) {
        if(bookDTO == null)
            return null;

        return Book.builder()
                .id(bookDTO.getId())
                .title(bookDTO.getTitle())
                .author(bookDTO.getAuthor())
                .genre(bookDTO.getGenre())
                .description(bookDTO.getDescription())
                .price(bookDTO.getPrice())
                .avgRating(bookDTO.getAvgRating())
                .stock(bookDTO.getStock())
                .reviews(bookDTO.getReviews() != null
                        ? bookDTO.getReviews()
                            .stream()
                            .map(ReviewMapper::toReview)
                            .collect(Collectors.toList())
                        : Collections.emptyList())
                .build();
    }
}
