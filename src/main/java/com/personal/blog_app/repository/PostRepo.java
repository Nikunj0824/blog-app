package com.personal.blog_app.repository;

import com.personal.blog_app.entity.Category;
import com.personal.blog_app.entity.Post;
import com.personal.blog_app.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepo extends JpaRepository<Post, Integer> {

    Page<Post> findByUser(User user, Pageable pageable);

    Page<Post> findByCategory(Category category, Pageable pageable);

    Page<Post> findByTitleContaining(String title, Pageable pageable);
}
