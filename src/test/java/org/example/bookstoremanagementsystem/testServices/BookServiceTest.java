package org.example.bookstoremanagementsystem.testServices;

import org.example.bookstoremanagementsystem.dto.BookDTO;
import org.example.bookstoremanagementsystem.mappers.BookMapper;
import org.example.bookstoremanagementsystem.model.entities.Book;
import org.example.bookstoremanagementsystem.model.entities.Review;
import org.example.bookstoremanagementsystem.repositories.BookRepository;
import org.example.bookstoremanagementsystem.services.BookService;
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
public class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookService bookService;

    private Book book;
    private BookDTO bookDTO;
    private Review review;

    @BeforeEach
    public void setUp() {
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
            Given there is a book with the given ID in the database
            Then the book details are returned
            """)
    public void test1() {
        when(bookRepository.findById(1)).thenReturn(Optional.of(book));

        BookDTO result = bookService.findBookById(1);

        assertNotNull(result);
        assertEquals(book.getId(), result.getId());
        assertEquals(book.getTitle(), result.getTitle());
        assertEquals(book.getAuthor(), result.getAuthor());
        assertEquals(book.getGenre(), result.getGenre());
        assertEquals(book.getDescription(), result.getDescription());
        assertEquals(book.getPrice(), result.getPrice());
        assertEquals(book.getAvgRating(), result.getAvgRating());
        assertEquals(book.getStock(), result.getStock());
        assertEquals(book.getReviews().size(), result.getReviews().size());
        assertEquals(book.getReviews().getFirst().getRating(), result.getReviews().getFirst().getRating());
        assertEquals(book.getReviews().getFirst().getComment(), result.getReviews().getFirst().getComment());

        verify(bookRepository, times(1)).findById(1);
    }

    @Test
    @DisplayName("""
            Given there isn't a book with the given ID in the database
            Then an exception is thrown
            """)
    public void test2() {
        when(bookRepository.findById(999)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> bookService.findBookById(999));

        verify(bookRepository, times(1)).findById(999);
    }

    @Test
    @DisplayName("""
            Given there are multiple entries of books in the database
            Then a list with all the books is returned
            """)
    public void test3() {
        when(bookRepository.findAll()).thenReturn(Arrays.asList(book));

        List<BookDTO> result = bookService.getAllBooks();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(book.getId(), result.getFirst().getId());
        assertEquals(book.getTitle(), result.getFirst().getTitle());
        assertEquals(book.getAuthor(), result.getFirst().getAuthor());
        assertEquals(book.getGenre(), result.getFirst().getGenre());
        assertEquals(book.getDescription(), result.getFirst().getDescription());
        assertEquals(book.getPrice(), result.getFirst().getPrice());
        assertEquals(book.getAvgRating(), result.getFirst().getAvgRating());
        assertEquals(book.getStock(), result.getFirst().getStock());
        assertEquals(book.getReviews().size(), result.getFirst().getReviews().size());
        assertEquals(book.getReviews().getFirst().getRating(), result.getFirst().getReviews().getFirst().getRating());
        assertEquals(book.getReviews().getFirst().getComment(), result.getFirst().getReviews().getFirst().getComment());

        verify(bookRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("""
            Given there is one or more books with the given title in the database
            Then a list containing details about each book is returned
            """)
    public void test4() {
        when(bookRepository.findByTitle("The Great Gatsby")).thenReturn(Arrays.asList(book));

        List<BookDTO> result = bookService.getBooksByTitle("The Great Gatsby");

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(book.getId(), result.getFirst().getId());
        assertEquals(book.getTitle(), result.getFirst().getTitle());
        assertEquals(book.getAuthor(), result.getFirst().getAuthor());
        assertEquals(book.getGenre(), result.getFirst().getGenre());
        assertEquals(book.getDescription(), result.getFirst().getDescription());
        assertEquals(book.getPrice(), result.getFirst().getPrice());
        assertEquals(book.getAvgRating(), result.getFirst().getAvgRating());
        assertEquals(book.getStock(), result.getFirst().getStock());
        assertEquals(book.getReviews().size(), result.getFirst().getReviews().size());
        assertEquals(book.getReviews().getFirst().getRating(), result.getFirst().getReviews().getFirst().getRating());
        assertEquals(book.getReviews().getFirst().getComment(), result.getFirst().getReviews().getFirst().getComment());

        verify(bookRepository, times(1)).findByTitle("The Great Gatsby");
    }

    @Test
    @DisplayName("""
            Given there is one or more books written by the given author in the database
            Then a list containing details about each book is returned
            """)
    public void test5() {
        when(bookRepository.findByAuthor("F. Scott Fitzgerald")).thenReturn(Arrays.asList(book));

        List<BookDTO> result = bookService.getBooksByAuthor("F. Scott Fitzgerald");

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(book.getId(), result.getFirst().getId());
        assertEquals(book.getTitle(), result.getFirst().getTitle());
        assertEquals(book.getAuthor(), result.getFirst().getAuthor());
        assertEquals(book.getGenre(), result.getFirst().getGenre());
        assertEquals(book.getDescription(), result.getFirst().getDescription());
        assertEquals(book.getPrice(), result.getFirst().getPrice());
        assertEquals(book.getAvgRating(), result.getFirst().getAvgRating());
        assertEquals(book.getStock(), result.getFirst().getStock());
        assertEquals(book.getReviews().size(), result.getFirst().getReviews().size());
        assertEquals(book.getReviews().getFirst().getRating(), result.getFirst().getReviews().getFirst().getRating());
        assertEquals(book.getReviews().getFirst().getComment(), result.getFirst().getReviews().getFirst().getComment());

        verify(bookRepository, times(1)).findByAuthor("F. Scott Fitzgerald");
    }

    @Test
    @DisplayName("""
            Given there is one or more books with the the given average rating in the database
            Then a list containing details about each book is returned
            """)
    public void test6() {
        when(bookRepository.findByAvgRating(4.5f)).thenReturn(Arrays.asList(book));

        List<BookDTO> result = bookService.getBooksByAvgRating(4.5f);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(book.getId(), result.getFirst().getId());
        assertEquals(book.getTitle(), result.getFirst().getTitle());
        assertEquals(book.getAuthor(), result.getFirst().getAuthor());
        assertEquals(book.getGenre(), result.getFirst().getGenre());
        assertEquals(book.getDescription(), result.getFirst().getDescription());
        assertEquals(book.getPrice(), result.getFirst().getPrice());
        assertEquals(book.getAvgRating(), result.getFirst().getAvgRating());
        assertEquals(book.getStock(), result.getFirst().getStock());
        assertEquals(book.getReviews().size(), result.getFirst().getReviews().size());
        assertEquals(book.getReviews().getFirst().getRating(), result.getFirst().getReviews().getFirst().getRating());
        assertEquals(book.getReviews().getFirst().getComment(), result.getFirst().getReviews().getFirst().getComment());

        verify(bookRepository, times(1)).findByAvgRating(4.5f);
    }

    @Test
    @DisplayName("""
            Given details of a book
            Then the books is inserted into the database
            """)
    public void test7() {
        when(bookRepository.save(any(Book.class))).thenReturn(book);

        BookDTO result = bookService.createBook(bookDTO);

        assertNotNull(result);
        assertEquals(book.getId(), result.getId());
        assertEquals(book.getTitle(), result.getTitle());
        assertEquals(book.getAuthor(), result.getAuthor());
        assertEquals(book.getGenre(), result.getGenre());
        assertEquals(book.getDescription(), result.getDescription());
        assertEquals(book.getPrice(), result.getPrice());
        assertEquals(book.getAvgRating(), result.getAvgRating());
        assertEquals(book.getStock(), result.getStock());
        assertEquals(book.getReviews().size(), result.getReviews().size());
        assertEquals(book.getReviews().getFirst().getRating(), result.getReviews().getFirst().getRating());
        assertEquals(book.getReviews().getFirst().getComment(), result.getReviews().getFirst().getComment());

        verify(bookRepository, times(1)).save(any(Book.class));
    }

    @Test
    @DisplayName("""
            Given there is a book with the given ID in the database
            Then the book is altered and saved with the new changes into the database
            """)
    public void test8() {
        when(bookRepository.findById(1)).thenReturn(Optional.of(book));
        when(bookRepository.save(any(Book.class))).thenReturn(book);

        bookDTO.setTitle("Updated Title");
        bookDTO.setAuthor("Updated Author");

        BookDTO result = bookService.updateBook(1, bookDTO);

        assertNotNull(result);
        assertEquals("Updated Title", result.getTitle());
        assertEquals("Updated Author", result.getAuthor());
        assertEquals(book.getGenre(), result.getGenre());
        assertEquals(book.getDescription(), result.getDescription());
        assertEquals(book.getPrice(), result.getPrice());
        assertEquals(book.getAvgRating(), result.getAvgRating());
        assertEquals(book.getStock(), result.getStock());
        assertEquals(book.getReviews().size(), result.getReviews().size());
        assertEquals(book.getReviews().getFirst().getRating(), result.getReviews().getFirst().getRating());
        assertEquals(book.getReviews().getFirst().getComment(), result.getReviews().getFirst().getComment());

        verify(bookRepository, times(1)).findById(1);
        verify(bookRepository, times(1)).save(any(Book.class));
    }

    @Test
    @DisplayName("""
            Given there is a book with the given ID in the database
            Then the book is deleted
            """)
    public void test9() {
        when(bookRepository.findById(1)).thenReturn(Optional.of(book));
        doNothing().when(bookRepository).delete(book);

        bookService.deleteBook(1);

        verify(bookRepository, times(1)).findById(1);
        verify(bookRepository, times(1)).delete(book);
    }
}