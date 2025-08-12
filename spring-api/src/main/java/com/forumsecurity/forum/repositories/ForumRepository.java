package com.forumsecurity.forum.repositories;

import com.forumsecurity.forum.models.Forum;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ForumRepository extends JpaRepository<Forum,Long> {
}
