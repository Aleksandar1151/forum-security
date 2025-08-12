package com.forumsecurity.forum.services;

import com.forumsecurity.forum.models.Comment;
import com.forumsecurity.forum.models.Forum;
import com.forumsecurity.forum.repositories.CommentRepository;
import com.forumsecurity.forum.repositories.ForumRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private ForumRepository forumRepository;

    public Comment addCommentToForum(Long forumId, Comment comment) {
        Forum forum = forumRepository.findById(forumId).orElseThrow(() -> new RuntimeException("Forum not found"));
        comment.setForum(forum);
        return commentRepository.save(comment);
    }

    public List<Comment> getCommentsByForumId(Long forumId) {
        return commentRepository.findByForumId(forumId);
    }
    // Get a comment by its ID
    public Comment getCommentById(Long commentId) {
        return commentRepository.findById(commentId).orElse(null);
    }

    // Update an existing comment
    public Comment updateComment(Comment updatedComment) {
        return commentRepository.save(updatedComment); // This will update the comment in the database
    }

    public void deleteCommentById(Long commentId) {
        if (commentRepository.existsById(commentId)) {
            commentRepository.deleteById(commentId);
        } else {
            throw new RuntimeException("Comment not found");
        }
    }
}
