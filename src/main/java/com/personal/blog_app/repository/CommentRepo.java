package com.personal.blog_app.repository;

import com.personal.blog_app.entity.Comment;
import com.personal.blog_app.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepo extends JpaRepository<Comment, Integer> {
    Page<Comment> findByUser(User user, Pageable pageable);
}
