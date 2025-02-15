package org.example.bookstoremanagementsystem.mappers;

import org.example.bookstoremanagementsystem.dto.ReviewDTO;
import org.example.bookstoremanagementsystem.model.entities.Review;

public class ReviewMapper {
    public static ReviewDTO toReviewDTO(Review review) {
        if(review == null)
            return null;

        return ReviewDTO.builder()
                .id(review.getId())
                .rating(review.getRating())
                .comment(review.getComment())
                .bookId(review.getBookId())
                .customerId(review.getCustomerId())
                .build();

    }

    public static Review toReview(ReviewDTO reviewDTO) {
        if(reviewDTO == null)
            return null;

        return Review.builder()
                .id(reviewDTO.getId())
                .rating(reviewDTO.getRating())
                .comment(reviewDTO.getComment())
                .bookId(reviewDTO.getBookId())
                .customerId(reviewDTO.getCustomerId())
                .build();

    }
}
