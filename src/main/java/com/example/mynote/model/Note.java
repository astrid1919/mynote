package com.example.mynote.model;

public class Note {
    private int id;
    private String title;
    private String content;
    private String createdAt;
    private String status;
    private String completedAt;

    public Note() {
    }

    public Note(int id, String title, String content, String createdAt, String status, String completedAt) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.createdAt = createdAt;
        this.status = status;
        this.completedAt = completedAt;
    }

    // Getters and setters
    public int getId() {
        return id;
    }   

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCompletedAt() {
        return completedAt;
    }

    public void setCompletedAt(String completedAt) {
        this.completedAt = completedAt;
    }
}
