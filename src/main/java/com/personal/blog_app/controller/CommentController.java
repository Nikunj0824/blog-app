package com.personal.blog_app.controller;

import com.personal.blog_app.dto.ApiResponseDto;
import com.personal.blog_app.dto.CommentDto;
import com.personal.blog_app.service.ICommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.personal.blog_app.controller.constant.ApiConstant.*;
import static com.personal.blog_app.controller.constant.ApiConstant.ASC;

@RestController
@RequestMapping(value = "/api")
public class CommentController {

    private final ICommentService commentService;

    public CommentController(ICommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping(value = "/post/{postId}/user/{userId}/comment/new")
    public ResponseEntity<CommentDto> createComment(@RequestBody CommentDto commentDto, @PathVariable int postId, @PathVariable int userId) {
        CommentDto comment = commentService.createComment(commentDto, postId, userId);
        return new ResponseEntity<>(comment, HttpStatus.CREATED);
    }

    @GetMapping(value = "/user/{userId}/comment")
    public ResponseEntity<List<CommentDto>> getAllCommentsByUser(
            @PathVariable int userId,
            @RequestParam(value = PAGE_NUMBER, defaultValue = ZERO, required = false) int pageNo,
            @RequestParam(value = PAGE_SIZE, defaultValue = ONE, required = false) int pageSize,
            @RequestParam(value = SORT_BY, defaultValue = ID, required = false) String sortBy,
            @RequestParam(value = SORT_DIR, defaultValue = ASC, required = false) String sortDir) {
        List<CommentDto> comments = commentService.getAllCommentsByUser(userId, pageNo, pageSize, sortBy, sortDir);
        return ResponseEntity.ok(comments);
    }

    @DeleteMapping("/comment/{commentId}")
    public ResponseEntity<ApiResponseDto> deleteComment(@PathVariable int commentId) {
        CommentDto commentDto = commentService.deleteComment(commentId);
        ApiResponseDto apiResponseDto = new ApiResponseDto(String.format("Comment: %s deleted successfully", commentDto.getId()), true);
        return ResponseEntity.ok(apiResponseDto);
    }
}
