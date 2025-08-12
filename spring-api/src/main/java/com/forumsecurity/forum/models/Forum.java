package com.forumsecurity.forum.models;

import jakarta.persistence.*;

import java.util.List;
@Entity
public class Forum {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String title;
    String description;
    @OneToMany(mappedBy = "forum")
   // @JoinColumn(name = "fk_comment_id", referencedColumnName = "id")
    List<Comment> comments;

    public Forum() {
    }

    public Forum(String title, String description, List<Comment> comments) {
        this.title = title;
        this.description = description;
        this.comments = comments;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public void addComment(Comment comment)
    {
        comments.add(comment);
    }
    @Override
    public String toString() {
        return "Forum{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", comments=" + comments +
                '}';
    }
}
