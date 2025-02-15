package org.example.bookstoremanagementsystem.repositories;

import org.example.bookstoremanagementsystem.dto.BookDTO;
import org.example.bookstoremanagementsystem.model.entities.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {

    @Query("SELECT b from Book b where b.title = :title")
    List<Book> findByTitle(String title);

    @Query("SELECT b from Book b where b.author = :author")
    List<Book> findByAuthor(String author);

    @Query("SELECT b from Book b where b.avgRating = :avgRating")
    List<Book> findByAvgRating(Float avgRating);
}
