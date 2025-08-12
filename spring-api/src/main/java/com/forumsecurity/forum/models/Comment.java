package com.forumsecurity.forum.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

@Entity
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String content;
    String author;
    String createdTime;
    String allowed;

    @ManyToOne
    @JoinColumn(name = "forum_id")
    @JsonBackReference
    private Forum forum;


    public Comment() {
    }

    public Comment(Long id, String content, String author, String createdTime, String allowed, Forum forum) {
        this.id = id;
        this.content = content;
        this.author = author;
        this.createdTime = createdTime;
        this.allowed = allowed;
        this.forum = forum;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", author='" + author + '\'' +
                ", createdTime='" + createdTime + '\'' +
                ", allowed='" + allowed + '\'' +
                '}';
    }

    public Forum getForum() {
        return forum;
    }

    public void setForum(Forum forum) {
        this.forum = forum;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }

    public String getAllowed() {
        return allowed;
    }

    public void setAllowed(String allowed) {
        this.allowed = allowed;
    }
}

