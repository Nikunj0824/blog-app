package com.personal.blog_app.service.impl;

import com.personal.blog_app.dto.PostDto;
import com.personal.blog_app.dto.PostResponse;
import com.personal.blog_app.entity.Category;
import com.personal.blog_app.entity.Post;
import com.personal.blog_app.entity.User;
import com.personal.blog_app.exeption.ResourceNotFoundException;
import com.personal.blog_app.repository.CategoryRepo;
import com.personal.blog_app.repository.PostRepo;
import com.personal.blog_app.repository.UserRepo;
import com.personal.blog_app.service.IPostService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

import static com.personal.blog_app.util.DtoEntityMapper.dtoToEntity;
import static com.personal.blog_app.util.DtoEntityMapper.entityToDto;

@Service
public class PostService implements IPostService {

    private static final String USER = "User";
    private static final String CATEGORY = "Category";
    private static final String POST = "Post";
    private static final String ID = "id";
    private static final String DSC = "dsc";

    private final PostRepo postRepo;
    private final UserRepo userRepo;
    private final CategoryRepo categoryRepo;

    public PostService(PostRepo postRepo, UserRepo userRepo, CategoryRepo categoryRepo) {
        this.postRepo = postRepo;
        this.userRepo = userRepo;
        this.categoryRepo = categoryRepo;
    }

    @Override
    public PostDto createPost(PostDto postDto, int userId, int categoryId) {
        User user = userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException(USER, ID, userId));
        Category category = categoryRepo.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category", ID, categoryId));
        Post post = dtoToEntity(postDto, Post.class);

        post.setImageUrl("default.png");
        post.setCreatedAt(new Date());
        post.setUser(user);
        post.setCategory(category);

        Post savedPost = postRepo.save(post);

        return entityToDto(savedPost, PostDto.class);
    }

    @Override
    public PostDto updatePost(PostDto postDto, int postId) {
        Post post = postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException(POST, ID, postId));

        post.setTitle(postDto.getTitle());
        post.setSubTitle(postDto.getSubTitle());
        post.setContent(postDto.getContent());
        post.setImageUrl(postDto.getImageUrl());

        return entityToDto(post, PostDto.class);
    }

    @Override
    public PostDto getPost(int postId) {
        Post post = postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException(POST, ID, postId));
        return entityToDto(post, PostDto.class);
    }

    @Override
    public PostResponse getAllPost(int pageNo, int pageSize, String sortBy, String sortDir) {

        Sort sort = (sortDir.equalsIgnoreCase(DSC)) ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        Page<Post> postPage = postRepo.findAll(pageable);
        List<Post> posts = postPage.getContent();

        PostResponse postResponse = new PostResponse();
        postResponse.setPostDtos(posts.stream().map(post -> entityToDto(post, PostDto.class)).toList());
        postResponse.setPageNo(pageNo);
        postResponse.setPageSize(pageSize);
        postResponse.setTotalElements(posts.size());
        postResponse.setTotalPages(postPage.getTotalPages());
        postResponse.setLastPage(postPage.isLast());

        return postResponse;
    }

    @Override
    public PostResponse getAllPostByUser(int userId, int pageNo, int pageSize, String sortBy, String sortDir) {
        User user = userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException(USER, ID, userId));

        Sort sort = (sortDir.equalsIgnoreCase(DSC)) ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        Page<Post> postPage = postRepo.findByUser(user, pageable);
        List<Post> posts = postPage.getContent();
        PostResponse postResponse = new PostResponse();
        postResponse.setPostDtos(posts.stream().map(post -> entityToDto(post, PostDto.class)).toList());
        postResponse.setPageNo(pageNo);
        postResponse.setPageSize(pageSize);
        postResponse.setTotalPages(postPage.getTotalPages());
        postResponse.setTotalElements(postPage.getNumberOfElements());
        postResponse.setLastPage(postPage.isLast());

        return postResponse;
    }

    @Override
    public PostResponse getAllPostByCategory(int categoryId, int pageNo, int pageSize, String sortBy, String sortDir) {
        Category category = categoryRepo.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException(CATEGORY, ID, categoryId));

        Sort sort = (sortDir.equalsIgnoreCase(DSC)) ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        Page<Post> postPage = postRepo.findByCategory(category, pageable);
        List<Post> posts = postPage.getContent();

        PostResponse postResponse = new PostResponse();
        postResponse.setPostDtos(posts.stream().map(post -> entityToDto(post, PostDto.class)).toList());
        postResponse.setPageNo(pageNo);
        postResponse.setPageSize(pageSize);
        postResponse.setTotalPages(postPage.getTotalPages());
        postResponse.setTotalElements(postPage.getNumberOfElements());
        postResponse.setLastPage(postPage.isLast());

        return postResponse;
    }

    @Override
    public PostResponse searchPost(String keyword, int pageNo, int pageSize, String sortBy, String sortDir) {
        Sort sort = (sortDir.equalsIgnoreCase(DSC)) ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        Page<Post> postPage = postRepo.findByTitleContaining(keyword, pageable);
        List<Post> posts = postPage.getContent();

        PostResponse postResponse = new PostResponse();
        postResponse.setPostDtos(posts.stream().map(post -> entityToDto(post, PostDto.class)).toList());
        postResponse.setPageNo(pageNo);
        postResponse.setPageSize(pageSize);
        postResponse.setTotalElements(postPage.getNumberOfElements());
        postResponse.setTotalPages(postPage.getTotalPages());
        postResponse.setLastPage(postPage.isLast());

        return postResponse;
    }

    @Override
    public PostDto deletePost(int postId) {
        Post post = postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException(POST, ID, postId));
        postRepo.deleteById(postId);
        return entityToDto(post, PostDto.class);
    }


}
