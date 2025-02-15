package org.example.bookstoremanagementsystem.services;


import org.example.bookstoremanagementsystem.dto.BookDTO;
import org.example.bookstoremanagementsystem.mappers.BookMapper;
import org.example.bookstoremanagementsystem.model.entities.Book;
import org.example.bookstoremanagementsystem.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    public BookDTO findBookById(int id) {
        Book book = bookRepository.findById(id).orElseThrow(() -> new RuntimeException("Book with id " + id + " not found"));
        return BookMapper.toBookDTO(book);
    }

    public List<BookDTO> getAllBooks() {
        List<Book> books = bookRepository.findAll();
        return books.stream().map(BookMapper::toBookDTO).collect(Collectors.toList());
    }

    public List<BookDTO> getBooksByTitle(String title) {
        List<Book> books = bookRepository.findByTitle(title);
        return books.stream().map(BookMapper::toBookDTO).collect(Collectors.toList());
    }

    public List<BookDTO> getBooksByAuthor(String author) {
        List<Book> books = bookRepository.findByAuthor(author);
        return books.stream().map(BookMapper::toBookDTO).collect(Collectors.toList());
    }

    public List<BookDTO> getBooksByAvgRating(Float rating) {
        List<Book> books = bookRepository.findByAvgRating(rating);
        return books.stream().map(BookMapper::toBookDTO).collect(Collectors.toList());
    }

    public BookDTO createBook(BookDTO bookDTO) {
        Book book = BookMapper.toBook(bookDTO);
        book.setReviews(null); //a new inserted book doesn't have any reviews
        return BookMapper.toBookDTO(bookRepository.save(book));
    }

    public BookDTO updateBook(int id, BookDTO bookDTO) {
        Book book = bookRepository.findById(id).orElseThrow(() -> new RuntimeException("Book with id " + id + " not found"));

        if(bookDTO.getTitle() != null)
            book.setTitle(bookDTO.getTitle());

        if(bookDTO.getAuthor() != null)
            book.setAuthor(bookDTO.getAuthor());

        if(bookDTO.getGenre() != null)
            book.setGenre(bookDTO.getGenre());

        if(bookDTO.getDescription() != null)
            book.setDescription(bookDTO.getDescription());

        if(bookDTO.getPrice() != null)
            book.setPrice(bookDTO.getPrice());

        if(bookDTO.getStock() != null)
            book.setStock(bookDTO.getStock());

        Book updatedBook = bookRepository.save(book);
        return BookMapper.toBookDTO(updatedBook);
    }

    public void deleteBook(int id) {
        Book book = bookRepository.findById(id).orElseThrow(() -> new RuntimeException("Book with id " + id + " not found"));
        bookRepository.delete(book);
    }
}
