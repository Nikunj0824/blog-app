package com.personal.blog_app.service.impl;

import com.personal.blog_app.dto.CommentDto;
import com.personal.blog_app.entity.Comment;
import com.personal.blog_app.entity.Post;
import com.personal.blog_app.entity.User;
import com.personal.blog_app.exeption.ResourceNotFoundException;
import com.personal.blog_app.repository.CommentRepo;
import com.personal.blog_app.repository.PostRepo;
import com.personal.blog_app.repository.UserRepo;
import com.personal.blog_app.service.ICommentService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.personal.blog_app.util.DtoEntityMapper.dtoToEntity;
import static com.personal.blog_app.util.DtoEntityMapper.entityToDto;

@Service
public class CommentService implements ICommentService {

    private final CommentRepo commentRepo;
    private final UserRepo userRepo;
    private final PostRepo postRepo;

    public CommentService(CommentRepo commentRepo, UserRepo userRepo, PostRepo postRepo) {
        this.commentRepo = commentRepo;
        this.userRepo = userRepo;
        this.postRepo = postRepo;
    }

    @Override
    public CommentDto createComment(CommentDto commentDto, int postId, int userId) {
        Post post = postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));
        User user = userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
        Comment comment = dtoToEntity(commentDto, Comment.class);
        comment.setPost(post);
        comment.setUser(user);
        Comment savedComment = commentRepo.save(comment);
        return entityToDto(savedComment, CommentDto.class);
    }

    @Override
    public List<CommentDto> getAllCommentsByUser(int userId, int pageNo, int pageSize, String sortBy, String sortDir) {
        User user = userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
        Sort sort = (sortDir.equalsIgnoreCase("dsc")) ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        Page<Comment> commentPage = commentRepo.findByUser(user, pageable);
        List<Comment> comments = commentPage.getContent();
        return comments.stream().map(comment -> entityToDto(comment, CommentDto.class)).toList();
    }

    @Override
    public CommentDto deleteComment(int commentId) {
        Comment comment = commentRepo.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("Comment", "id", commentId));
        commentRepo.deleteById(commentId);
        return entityToDto(comment, CommentDto.class);
    }
}
