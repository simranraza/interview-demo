package com.imranraza.interview_demo.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;


@Entity
@Table(name = "authors")
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @OneToMany(mappedBy = "author",
               cascade = CascadeType.ALL,
               orphanRemoval = true)
    private List<Book> books = new ArrayList<>();

    protected Author() {
        // JPA only
    }

    public Author(String name) {
        this.name = name;
    }

    // getters

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Book> getBooks() {
        return books;
    }

    // optional helpers

    public void addBook(Book book) {
        books.add(book);
        book.setAuthor(this);
    }

    public void removeBook(Book book) {
        books.remove(book);
        book.setAuthor(null);
    }
}