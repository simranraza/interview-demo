package com.imranraza.interview_demo.service;

import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.imranraza.interview_demo.dto.BookDto;
import com.imranraza.interview_demo.dto.CreateBookRequest;
import com.imranraza.interview_demo.entity.Author;
import com.imranraza.interview_demo.entity.Book;
import com.imranraza.interview_demo.repository.AuthorRepository;
import com.imranraza.interview_demo.repository.BookRepository;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class BookService {

     private static final Logger log = LoggerFactory.getLogger(BookService.class);


    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    public BookService(BookRepository bookRepository,
                       AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }

    @Transactional
    public BookDto createBook(CreateBookRequest request) {
        Author author = authorRepository.findById(request.getAuthorId())
                .orElseThrow(() -> new IllegalArgumentException("Author not found: " + request.getAuthorId()));

        Book book = new Book(request.getTitle(), author);
        Book saved = bookRepository.save(book);
        return toDto(saved);
    }

    @Transactional(readOnly = true)
    public BookDto getBook(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Book not found: " + id));
        BookDto bookDto =  toDto(book);

         // log the DTO (use debug; adjust level as needed)
        log.debug("getBook returned: id={}, title={}, authorId={}",
                  bookDto.getId(), bookDto.getTitle(), bookDto.getAuthorName());

        return bookDto;
    }

    @Transactional(readOnly = true)
    public List<BookDto> getBooksByAuthor(Long authorId) {
        Author author = authorRepository.findById(authorId)
                .orElseThrow(() -> new IllegalArgumentException("Author not found: " + authorId));

        List<Book> books = bookRepository.findByAuthorId(author.getId());
        return books.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    private BookDto toDto(Book book) {
        // assumes BookDto has a constructor BookDto(Long id, String title, Long authorId)
        return new BookDto(book.getId(), book.getTitle(), book.getAuthor().getName());
    }
}