package org.example.bookstoremanagementsystem.services;

import org.example.bookstoremanagementsystem.dto.ReviewDTO;
import org.example.bookstoremanagementsystem.mappers.ReviewMapper;
import org.example.bookstoremanagementsystem.model.entities.Book;
import org.example.bookstoremanagementsystem.model.entities.Review;
import org.example.bookstoremanagementsystem.repositories.BookRepository;
import org.example.bookstoremanagementsystem.repositories.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    BookRepository bookRepository;

    public void updateBookAvgRating(int id){
        Book book = bookRepository.findById(id).orElse(null);

        if(book != null) {
            List<Review> reviews = book.getReviews();

            float avgRating = 0.0f;
            for (Review review : reviews)
                avgRating += review.getRating();

            avgRating = (float)(avgRating / reviews.size());

            book.setAvgRating(avgRating);
            bookRepository.save(book);
        }

    }

    public ReviewDTO findReviewById(int id) {
        Review review = reviewRepository.findById(id).orElseThrow(() -> new RuntimeException("Review with id " + id + " not found"));
        return ReviewMapper.toReviewDTO(review);
    }

    public List<ReviewDTO> getReviewsByBookId(String title) {
        Book book = bookRepository.findByTitle(title).getFirst();
        List<Review> reviews = reviewRepository.findReviewsByBook(book.getId());
        return reviews.stream().map(ReviewMapper::toReviewDTO).collect(Collectors.toList());
    }

    public ReviewDTO createReview(ReviewDTO reviewDTO) {
        Review review = ReviewMapper.toReview(reviewDTO);
        ReviewDTO createdReview = ReviewMapper.toReviewDTO(reviewRepository.save(review));

        updateBookAvgRating(createdReview.getBookId());

        return createdReview;
    }

    public ReviewDTO updateReview(int id, ReviewDTO reviewDTO) {
        Review review = reviewRepository.findById(id).orElseThrow(() -> new RuntimeException("Review with id " + id + " not found"));

        if(reviewDTO.getRating() != null)
            review.setRating(reviewDTO.getRating());

        if(reviewDTO.getComment() != null)
            review.setComment(reviewDTO.getComment());

        Review updatedReview = reviewRepository.save(review);
        return ReviewMapper.toReviewDTO(updatedReview);
    }

    public void deleteReview(int id) {
        Review review = reviewRepository.findById(id).orElseThrow(() -> new RuntimeException("Review with id " + id + " not found"));
        updateBookAvgRating(review.getBookId());
        reviewRepository.delete(review);
    }
}
