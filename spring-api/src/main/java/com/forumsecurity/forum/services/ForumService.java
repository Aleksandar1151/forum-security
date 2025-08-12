package com.forumsecurity.forum.services;

import com.forumsecurity.forum.models.Forum;
import com.forumsecurity.forum.repositories.ForumRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ForumService {
    @Autowired
    private ForumRepository forumRepository;

    public List<Forum> getAllForums() {
        return forumRepository.findAll();
    }

    public Forum getForumById(Long id) {
        return forumRepository.findById(id).orElse(null);
    }

    public Forum createForum(Forum forum) {
        return forumRepository.save(forum);
    }
}
