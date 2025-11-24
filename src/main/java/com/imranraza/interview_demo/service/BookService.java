package com.imranraza.interview_demo.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.imranraza.interview_demo.dto.CreateBookRequest;
import com.imranraza.interview_demo.entity.Author;
import com.imranraza.interview_demo.entity.Book;
import com.imranraza.interview_demo.repository.AuthorRepository;
import com.imranraza.interview_demo.repository.BookRepository;

import java.util.List;

@Service
public class BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    public BookService(BookRepository bookRepository,
                       AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }

    @Transactional
    public Book createBook(CreateBookRequest request) {
        Author author = authorRepository.findById(request.getAuthorId())
                .orElseThrow(() -> new IllegalArgumentException("Author not found: " + request.getAuthorId()));

        Book book = new Book(request.getTitle(), author);
        return bookRepository.save(book);
    }

    @Transactional(readOnly = true)
    public Book getBook(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Book not found: " + id));
    }

    @Transactional(readOnly = true)
    public List<Book> getBooksByAuthor(Long authorId) {
        // You could also validate the author exists first
        return bookRepository.findByAuthorId(authorId);
    }
}