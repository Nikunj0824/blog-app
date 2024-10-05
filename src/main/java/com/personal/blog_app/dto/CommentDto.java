package com.personal.blog_app.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class CommentDto {
    private int id;

    @NotEmpty
    private String content;

    private UserDto user;
}
