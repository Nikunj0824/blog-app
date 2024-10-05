package com.personal.blog_app.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

@Data
public class PostDto {

    private int id;

    @NotEmpty
    private String title;

    private String subTitle;

    @NotEmpty
    private String content;

    @DateTimeFormat
    private Date createdAt;

    private String imageUrl;

    private UserDto user;

    private CategoryDto category;

    private List<CommentDto> comments;

}
