package org.example.bookstoremanagementsystem.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.bookstoremanagementsystem.dto.ReviewDTO;
import org.example.bookstoremanagementsystem.services.ReviewService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reviews")
@Tag(name = "Review", description = "Endpoints for managing reviews")
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;

    @Operation(summary = "Get a review by ID", description = "Retrieves details about a review using the review's ID")
    @GetMapping("/{id}")
    public ResponseEntity<ReviewDTO> getReviewById(@PathVariable int id) {
        ReviewDTO reviewDTO = reviewService.findReviewById(id);
        return new ResponseEntity<>(reviewDTO, HttpStatus.OK);
    }

    @Operation(summary = "Get all reviews for a certain book", description = "Retrieves a list of reviews that belong to the specified book")
    @GetMapping("/searchByBook")
    public ResponseEntity<List<ReviewDTO>> getReviewsByBook(@RequestParam("Book Title") String bookTitle) {
        reviewService.getReviewsByBookId(bookTitle);
        return new ResponseEntity<>(reviewService.getReviewsByBookId(bookTitle), HttpStatus.OK);
    }

    @Operation(summary = "Add a new review", description = "Adds a new review to a book. Customer and books fields are required")
    @PostMapping
    public ResponseEntity<ReviewDTO> addReview(@Valid @RequestBody ReviewDTO reviewDTO) {
        ReviewDTO review = reviewService.createReview(reviewDTO);
        return new ResponseEntity<>(review, HttpStatus.CREATED);
    }

    @Operation(summary = "Update a posted review", description = "Updates the details of a posted review")
    @PutMapping("/{id}")
    public ResponseEntity<ReviewDTO> updateReview(@PathVariable int id, @Valid @RequestBody ReviewDTO reviewDTO) {
        ReviewDTO review = reviewService.updateReview(id, reviewDTO);
        return new ResponseEntity<>(review, HttpStatus.OK);
    }

    @Operation(summary = "Delete a review", description = "Deletes a review by ID from the database")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReview(@PathVariable int id) {
        reviewService.deleteReview(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
