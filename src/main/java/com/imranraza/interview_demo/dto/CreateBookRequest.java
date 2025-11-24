package com.imranraza.interview_demo.dto;

public class CreateBookRequest {

    private String title;
    private Long authorId;

    public CreateBookRequest() {
    }

    public CreateBookRequest(String title, Long authorId) {
        this.title = title;
        this.authorId = authorId;
    }

    public String getTitle() {
        return title;
    }

    public Long getAuthorId() {
        return authorId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
    }
}