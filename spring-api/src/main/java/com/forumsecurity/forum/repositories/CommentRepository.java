package com.forumsecurity.forum.repositories;

import com.forumsecurity.forum.models.Comment;
import com.forumsecurity.forum.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment,Long> {
    List<Comment> findByForumId(Long forumId);
}
