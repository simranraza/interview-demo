package com.imranraza.interview_demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.imranraza.interview_demo.entity.Book;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {

    List<Book> findByAuthorId(Long authorId);
}
