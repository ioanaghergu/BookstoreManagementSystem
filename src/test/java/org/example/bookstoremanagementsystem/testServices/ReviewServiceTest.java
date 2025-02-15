package org.example.bookstoremanagementsystem.testServices;

import org.example.bookstoremanagementsystem.dto.ReviewDTO;
import org.example.bookstoremanagementsystem.model.entities.Book;
import org.example.bookstoremanagementsystem.model.entities.Review;
import org.example.bookstoremanagementsystem.repositories.BookRepository;
import org.example.bookstoremanagementsystem.repositories.ReviewRepository;
import org.example.bookstoremanagementsystem.services.ReviewService;
import org.example.bookstoremanagementsystem.mappers.ReviewMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ReviewServiceTest {

    @Mock
    private ReviewRepository reviewRepository;

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private ReviewService reviewService;

    private Review review;
    private Book book;
    private ReviewDTO reviewDTO;

    @BeforeEach
    public void setUp() {
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
            Given there is a review with the given ID in the database
            Then the review details are returned
            """)
    public void test1() {
        when(reviewRepository.findById(1)).thenReturn(Optional.of(review));

        ReviewDTO result = reviewService.findReviewById(1);

        assertNotNull(result);
        assertEquals(review.getId(), result.getId());
        assertEquals(review.getRating(), result.getRating());
        assertEquals(review.getComment(), result.getComment());
        assertEquals(review.getBookId(), result.getBookId());
        assertEquals(review.getCustomerId(), result.getCustomerId());

        verify(reviewRepository, times(1)).findById(1);
    }

    @Test
    @DisplayName("""
            Given there isn't a review with the given ID in the database
            Then an exception is thrown
            """)
    public void test2() {
        when(reviewRepository.findById(999)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> reviewService.findReviewById(999));

        verify(reviewRepository, times(1)).findById(999);
    }

    @Test
    @DisplayName("""
            Given there is a book with the given title in the databse
            Then a list of reviews for that book is returned
            """)
    public void test3() {
        when(bookRepository.findByTitle("Test Book")).thenReturn(Arrays.asList(book));
        when(reviewRepository.findReviewsByBook(1)).thenReturn(Arrays.asList(review));

        List<ReviewDTO> result = reviewService.getReviewsByBookId("Test Book");

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(review.getId(), result.getFirst().getId());
        assertEquals(review.getRating(), result.getFirst().getRating());
        assertEquals(review.getComment(), result.getFirst().getComment());

        verify(bookRepository, times(1)).findByTitle("Test Book");
        verify(reviewRepository, times(1)).findReviewsByBook(1);
    }

    @Test
    @DisplayName("""
            Given details of a review
            Then a new review is created and saved in the database
            """)
    public void test4() {
        when(reviewRepository.save(any(Review.class))).thenReturn(review);
        when(bookRepository.findById(1)).thenReturn(Optional.of(book));

        ReviewDTO result = reviewService.createReview(reviewDTO);

        assertNotNull(result);
        assertEquals(review.getId(), result.getId());
        assertEquals(review.getRating(), result.getRating());
        assertEquals(review.getComment(), result.getComment());
        assertEquals(review.getBookId(), result.getBookId());
        assertEquals(review.getCustomerId(), result.getCustomerId());

        verify(reviewRepository, times(1)).save(any(Review.class));
        verify(bookRepository, times(1)).save(any(Book.class));
    }

    @Test
    @DisplayName("""
            Given the review with the given ID exists in the database
            Then the review's detailes are updated and changes are saved in the database
            """)
    public void test5() {
        when(reviewRepository.findById(1)).thenReturn(Optional.of(review));
        when(reviewRepository.save(any(Review.class))).thenReturn(review);

        reviewDTO.setRating(5);
        reviewDTO.setComment("Amazing book!");

        ReviewDTO result = reviewService.updateReview(1, reviewDTO);

        assertNotNull(result);
        assertEquals(5, result.getRating());
        assertEquals("Amazing book!", result.getComment());

        verify(reviewRepository, times(1)).findById(1);
        verify(reviewRepository, times(1)).save(any(Review.class));
    }

    @Test
    @DisplayName("""
            Given there is a review with the given ID in the database
            Then the review is deleted from the database
            """)
    public void test6() {
        when(reviewRepository.findById(1)).thenReturn(Optional.of(review));
        doNothing().when(reviewRepository).delete(any(Review.class));

        reviewService.deleteReview(1);

        verify(reviewRepository, times(1)).findById(1);
        verify(reviewRepository, times(1)).delete(any(Review.class));
    }
}
