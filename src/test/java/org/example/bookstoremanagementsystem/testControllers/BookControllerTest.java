package org.example.bookstoremanagementsystem.testControllers;

import org.example.bookstoremanagementsystem.controllers.BookController;
import org.example.bookstoremanagementsystem.dto.BookDTO;
import org.example.bookstoremanagementsystem.mappers.BookMapper;
import org.example.bookstoremanagementsystem.model.entities.Book;
import org.example.bookstoremanagementsystem.model.entities.Review;
import org.example.bookstoremanagementsystem.services.BookService;
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
public class BookControllerTest {

    @Mock
    private BookService bookService;

    @InjectMocks
    private BookController bookController;

    private BookDTO bookDTO;
    private Book book;
    private Review review;

    @BeforeEach
    void setUp() {
        review = Review.builder()
                .id(1)
                .rating(5)
                .comment("Excellent book!")
                .bookId(1)
                .customerId(1)
                .build();

        book = Book.builder()
                .id(1)
                .title("The Great Gatsby")
                .author("F. Scott Fitzgerald")
                .genre("Fiction")
                .description("A story of the American dream.")
                .price(10.99f)
                .avgRating(4.5f)
                .stock(100)
                .reviews(Arrays.asList(review))
                .build();

        bookDTO = BookMapper.toBookDTO(book);
    }

    @Test
    @DisplayName("""
            Given there is a book in the database with the requested ID
            Then details of the book and status 200 are returned
            """)
    void test1() {
        when(bookService.findBookById(1)).thenReturn(bookDTO);

        ResponseEntity<BookDTO> response = bookController.getBookById(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(bookDTO, response.getBody());
        verify(bookService, times(1)).findBookById(1);
    }

    @DisplayName("""
            Given there is a request to display all the books from the database
            Then a list with details of each book and status 200 are returned
            """)
    void test2() {
        List<BookDTO> books = Arrays.asList(bookDTO);
        when(bookService.getAllBooks()).thenReturn(books);

        ResponseEntity<List<BookDTO>> response = bookController.getAllBooks();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(books, response.getBody());
        verify(bookService, times(1)).getAllBooks();
    }

    @Test
    @DisplayName("""
            Given there are books with the requested title
            Then a list with details of each book ans status 200 are returned
            """)
    void test3() {
        List<BookDTO> books = Arrays.asList(bookDTO);
        when(bookService.getBooksByTitle("The Great Gatsby")).thenReturn(books);

        ResponseEntity<List<BookDTO>> response = bookController.getBooksByTitle("The Great Gatsby");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(books, response.getBody());
        verify(bookService, times(1)).getBooksByTitle("The Great Gatsby");
    }

    @Test
    @DisplayName("""
            Given there are books written by the requested author
            Then a list with details of each book ans status 200 are returned
            """)
    void test4() {
        List<BookDTO> books = Arrays.asList(bookDTO);
        when(bookService.getBooksByAuthor("F. Scott Fitzgerald")).thenReturn(books);

        ResponseEntity<List<BookDTO>> response = bookController.getBooksByAuthor("F. Scott Fitzgerald");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(books, response.getBody());
        verify(bookService, times(1)).getBooksByAuthor("F. Scott Fitzgerald");
    }

    @Test
    @DisplayName("""
            Given there are books with by the requested average rating
            Then a list with details of each book ans status 200 are returned
            """)
    void test5() {
        List<BookDTO> books = Arrays.asList(bookDTO);
        when(bookService.getBooksByAvgRating(4.5f)).thenReturn(books);

        ResponseEntity<List<BookDTO>> response = bookController.getBooksByRating(4.5f);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(books, response.getBody());
        verify(bookService, times(1)).getBooksByAvgRating(4.5f);
    }

    @Test
    @DisplayName("""
            Given there is a request to create a book with valid details
            Then the created book and status 201 are returned
            """)
    void test6() {
        when(bookService.createBook(bookDTO)).thenReturn(bookDTO);

        ResponseEntity<BookDTO> response = bookController.addBook(bookDTO);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(bookDTO, response.getBody());
        verify(bookService, times(1)).createBook(bookDTO);
    }

    @Test
    @DisplayName("""
            Given there is a valid request for updating a book
            Then the book is updated correspondingly and status 200 is returned
            """)
    void test7() {
        when(bookService.updateBook(1, bookDTO)).thenReturn(bookDTO);

        ResponseEntity<BookDTO> response = bookController.updateBook(1, bookDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(bookDTO, response.getBody());
        verify(bookService, times(1)).updateBook(1, bookDTO);
    }

    @Test
    @DisplayName("""
            Given there is a request for deleting a book and the book exists in the database
            Then the book is deleted and status 204 is returned
            """)
    void test8() {
        doNothing().when(bookService).deleteBook(1);

        ResponseEntity<Void> response = bookController.deleteBook(1);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(bookService, times(1)).deleteBook(1);
    }
}