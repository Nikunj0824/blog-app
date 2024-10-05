package com.personal.blog_app.service;

import com.personal.blog_app.dto.CommentDto;

import java.util.List;

public interface ICommentService {

    CommentDto createComment(CommentDto commentDto, int postId, int userId);

    List<CommentDto> getAllCommentsByUser(int userId, int pageNo, int pageSize, String sortBy, String sortDir);

    CommentDto deleteComment(int commentId);
}
