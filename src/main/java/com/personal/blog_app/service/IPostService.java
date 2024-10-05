package com.personal.blog_app.service;

import com.personal.blog_app.dto.PostDto;
import com.personal.blog_app.dto.PostResponse;

public interface IPostService {

    PostDto createPost(PostDto postDto, int userId, int categoryId);

    PostDto updatePost(PostDto postDto, int postId);

    PostDto getPost(int postId);

    PostResponse getAllPost(int pageNo, int pageSize, String sortBy, String sortDir);

    PostResponse getAllPostByUser(int userId, int pageNo, int pageSize, String sortBy, String sortDir);

    PostResponse getAllPostByCategory(int categoryId, int pageNo, int pageSize, String sortBy, String sortDir);

    PostResponse searchPost(String keyword, int pageNo, int pageSize, String sortBy, String sortDir);

    PostDto deletePost(int postId);
}
