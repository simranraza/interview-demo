package com.imranraza.interview_demo;


import com.imranraza.interview_demo.dto.BookDto;
import com.imranraza.interview_demo.dto.CreateBookRequest;
import com.imranraza.interview_demo.entity.Author;
import com.imranraza.interview_demo.entity.Book;
import com.imranraza.interview_demo.repository.AuthorRepository;
import com.imranraza.interview_demo.repository.BookRepository;
import com.imranraza.interview_demo.service.BookService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @Mock
    private AuthorRepository authorRepository;

    @InjectMocks
    private BookService bookService;

    private Author author;

    @BeforeEach
    void setUp() {
        author = new Author("Jane Doe");
        setId(author, 10L);
    }

    @Test
    void createBook_returnsBookDto() {
        CreateBookRequest req = new CreateBookRequest();
        // set fields via reflection if CreateBookRequest has no public setters/constructors
        try {
            Field fTitle = CreateBookRequest.class.getDeclaredField("title");
            fTitle.setAccessible(true);
            fTitle.set(req, "My Book");
            Field fAuthorId = CreateBookRequest.class.getDeclaredField("authorId");
            fAuthorId.setAccessible(true);
            fAuthorId.set(req, author.getId());
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }

        when(authorRepository.findById(author.getId())).thenReturn(Optional.of(author));
        // capture saved book and return with id set
        when(bookRepository.save(any(Book.class))).thenAnswer(invocation -> {
            Book b = invocation.getArgument(0);
            setId(b, 100L);
            return b;
        });

        BookDto dto = bookService.createBook(req);

        assertNotNull(dto);
        assertEquals(100L, dto.getId());
        assertEquals("My Book", dto.getTitle());
        assertEquals("Jane Doe", dto.getAuthorName());

        ArgumentCaptor<Book> captor = ArgumentCaptor.forClass(Book.class);
        verify(bookRepository).save(captor.capture());
        assertEquals("My Book", captor.getValue().getTitle());
    }

    @Test
    void getBook_returnsBookDto() {
        Book book = new Book("Title A", author);
        setId(book, 42L);

        when(bookRepository.findById(42L)).thenReturn(Optional.of(book));

        BookDto dto = bookService.getBook(42L);

        assertNotNull(dto);
        assertEquals(42L, dto.getId());
        assertEquals("Title A", dto.getTitle());
        assertEquals("Jane Doe", dto.getAuthorName());

        verify(bookRepository).findById(42L);
    }

    @Test
    void getBook_notFound_throws() {
        when(bookRepository.findById(999L)).thenReturn(Optional.empty());
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> bookService.getBook(999L));
        assertTrue(ex.getMessage().contains("Book not found"));
    }

    @Test
    void getBooksByAuthor_returnsListOfDtos() {
        Book b1 = new Book("B1", author);
        Book b2 = new Book("B2", author);
        setId(b1, 1L);
        setId(b2, 2L);

        when(authorRepository.findById(author.getId())).thenReturn(Optional.of(author));
        when(bookRepository.findByAuthorId(author.getId())).thenReturn(List.of(b1, b2));

        List<BookDto> dtos = bookService.getBooksByAuthor(author.getId());

        assertNotNull(dtos);
        assertEquals(2, dtos.size());
        assertEquals("B1", dtos.get(0).getTitle());
        assertEquals("B2", dtos.get(1).getTitle());
    }

    @Test
    void getBooksByAuthor_authorNotFound_throws() {
        when(authorRepository.findById(123L)).thenReturn(Optional.empty());
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> bookService.getBooksByAuthor(123L));
        assertTrue(ex.getMessage().contains("Author not found"));
    }

    // helper to set private id field on entities used in tests
    private static void setId(Object entity, Long id) {
        try {
            Field field = entity.getClass().getDeclaredField("id");
            field.setAccessible(true);
            field.set(entity, id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
