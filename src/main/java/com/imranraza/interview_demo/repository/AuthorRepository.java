package com.imranraza.interview_demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.imranraza.interview_demo.entity.Author;

public interface AuthorRepository extends JpaRepository<Author, Long> {
}
