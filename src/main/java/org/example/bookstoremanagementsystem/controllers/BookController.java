package org.example.bookstoremanagementsystem.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.bookstoremanagementsystem.dto.BookDTO;
import org.example.bookstoremanagementsystem.services.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
@Tag(name = "Book", description = "Endpoints for managing books")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @Operation(summary = "Get a book by ID", description = "Retrieves details about a book using the book's ID")
    @GetMapping("/{id}")
    public ResponseEntity<BookDTO> getBookById(@PathVariable int id) {
        BookDTO bookDTO = bookService.findBookById(id);
        return new ResponseEntity<>(bookDTO, HttpStatus.OK);
    }

    @Operation(summary = "Get a list of all books", description = "Retrieves all books from the databse")
    @GetMapping
    public ResponseEntity<List<BookDTO>> getAllBooks() {
        List<BookDTO> books = bookService.getAllBooks();
        return new ResponseEntity<>(books, HttpStatus.OK);
    }

    @Operation(summary = "Find books by title", description = "Retrieves a list of books with the specified title")
    @GetMapping("/title")
    public ResponseEntity<List<BookDTO>> getBooksByTitle(@RequestParam("title") String title) {
        List<BookDTO> books = bookService.getBooksByTitle(title);
        return new ResponseEntity<>(books, HttpStatus.OK);
    }

    @Operation(summary = "Find books by author", description = "Retrieves a list of books written by the specifies author")
    @GetMapping("/author")
    public ResponseEntity<List<BookDTO>> getBooksByAuthor(@RequestParam("author") String author) {
        List<BookDTO> books = bookService.getBooksByAuthor(author);
        return new ResponseEntity<>(books, HttpStatus.OK);
    }


    @Operation(summary = "Find books by rating", description = "Retrieves a list of books with the specified average rating")
    @GetMapping("/rating")
    public ResponseEntity<List<BookDTO>> getBooksByRating(@RequestParam("avgRating") Float rating) {
        List<BookDTO> books = bookService.getBooksByAvgRating(rating);
        return new ResponseEntity<>(books, HttpStatus.OK);
    }


    @Operation(summary = "Add a new book", description = "Adds a new book to the bookstore's database. Make sure the list reviews is empty, a book can't have reviews if it's inserted only now into the database.")
    @PostMapping
    public ResponseEntity<BookDTO> addBook(@Valid @RequestBody BookDTO bookDTO) {
        BookDTO book = bookService.createBook(bookDTO);
        return new ResponseEntity<>(book, HttpStatus.CREATED);
    }

    @Operation(summary = "Update details of a book", description = "Updates the details of a book from the database")
    @PutMapping("/{id}")
    public ResponseEntity<BookDTO> updateBook(@PathVariable int id, @Valid @RequestBody BookDTO bookDTO) {
        BookDTO book = bookService.updateBook(id, bookDTO);
        return new ResponseEntity<>(book, HttpStatus.OK);
    }

    @Operation(summary = "Delete a book", description = "Deletes a book by ID from the database")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable int id) {
        bookService.deleteBook(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
