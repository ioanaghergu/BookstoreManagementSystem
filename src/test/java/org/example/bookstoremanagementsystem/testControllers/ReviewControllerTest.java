package org.example.bookstoremanagementsystem.testControllers;

import org.example.bookstoremanagementsystem.controllers.ReviewController;
import org.example.bookstoremanagementsystem.dto.ReviewDTO;
import org.example.bookstoremanagementsystem.mappers.ReviewMapper;
import org.example.bookstoremanagementsystem.model.entities.Book;
import org.example.bookstoremanagementsystem.model.entities.Review;
import org.example.bookstoremanagementsystem.services.ReviewService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ReviewControllerTest {

    @Mock
    private ReviewService reviewService;

    @InjectMocks
    private ReviewController reviewController;

    private ReviewDTO reviewDTO;
    private Review review;
    private Book book;

    @BeforeEach
    void setUp() {
        review = Review.builder()
                .id(1)
                .rating(4)
                .comment("Great book!")
                .bookId(1)
                .customerId(1)
                .build();

        book = Book.builder()
                .id(1)
                .title("Test Book")
                .reviews(Arrays.asList(review))
                .avgRating(4.0f)
                .build();

        reviewDTO = ReviewMapper.toReviewDTO(review);
    }

    @Test
    @DisplayName("""
            Given there is a reviewr in the database with the requested ID
            Then details of the review and status 200 are returned
            """)
    void test1() {
        when(reviewService.findReviewById(1)).thenReturn(reviewDTO);

        ResponseEntity<ReviewDTO> response = reviewController.getReviewById(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(reviewDTO, response.getBody());
        verify(reviewService, times(1)).findReviewById(1);
    }

    @Test
    @DisplayName("""
            Given there is a request to display all the reviews for a certain book
            Then a list of all the reviews and status 200 are returned
            """)
    void test2() {
        List<ReviewDTO> reviews = Arrays.asList(reviewDTO);
        when(reviewService.getReviewsByBookId("Test Book")).thenReturn(reviews);

        ResponseEntity<List<ReviewDTO>> response = reviewController.getReviewsByBook("Test Book");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(reviews, response.getBody());
    }

    @Test
    @DisplayName("""
            Given there is a request to add a review to a book
            Then the created review and status 201 are returned
            """)
    void test3() {
        when(reviewService.createReview(reviewDTO)).thenReturn(reviewDTO);

        ResponseEntity<ReviewDTO> response = reviewController.addReview(reviewDTO);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(reviewDTO, response.getBody());
        verify(reviewService, times(1)).createReview(reviewDTO);
    }

    @Test
    @DisplayName("""
            Given there is a valid request for updating a review's details
            Then the updated review and status 200 are returned
            """)
    void test4() {
        when(reviewService.updateReview(1, reviewDTO)).thenReturn(reviewDTO);

        ResponseEntity<ReviewDTO> response = reviewController.updateReview(1, reviewDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(reviewDTO, response.getBody());
        verify(reviewService, times(1)).updateReview(1, reviewDTO);
    }

    @Test
    @DisplayName("""
            Given there is a request for deleting a review
            Then the review is deleted and status 204 is returned
            """)
    void test5() {
        doNothing().when(reviewService).deleteReview(1);

        ResponseEntity<Void> response = reviewController.deleteReview(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(reviewService, times(1)).deleteReview(1);
    }
}