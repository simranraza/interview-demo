package com.imranraza.interview_demo.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.imranraza.interview_demo.dto.BookDto;
import com.imranraza.interview_demo.dto.CreateBookRequest;
import com.imranraza.interview_demo.entity.Book;
import com.imranraza.interview_demo.service.BookService;

@RestController
@RequestMapping("/api/books")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    
    @PostMapping
    public ResponseEntity<BookDto> createBook(@RequestBody CreateBookRequest request) {
        BookDto created = bookService.createBook(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    
    @GetMapping("/{id}")
    public ResponseEntity<BookDto> getBook(@PathVariable Long id) {
        BookDto bookDto = bookService.getBook(id);
        return ResponseEntity.ok(bookDto);
    }

    @GetMapping("/byAuthor/{authorId}")
    public ResponseEntity<List<BookDto>> getBooksByAuthor(@PathVariable Long authorId) {
        List<BookDto> books = bookService.getBooksByAuthor(authorId);
        return ResponseEntity.ok(books);
    }
}