package org.example.bookstoremanagementsystem.repositories;

import org.example.bookstoremanagementsystem.model.entities.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Integer> {

    @Query("SELECT r from Review r where r.bookId = :bookId")
    List<Review> findReviewsByBook(Integer bookId);
}
