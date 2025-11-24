package com.imranraza.interview_demo.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.*;

@Entity
@Table(name = "books")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id", nullable = false)
    @JsonBackReference
    private Author author;

    protected Book() {
        // JPA only
    }

    public Book(String title, Author author) {
        this.title = title;
        this.author = author;
    }

    // getters

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public Author getAuthor() {
        return author;
    }

    // setter only if you need it for relationship handling
    public void setAuthor(Author author) {
        this.author = author;
    }
}