package com.personal.blog_app.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class PostResponse {

    @JsonProperty(value = "posts")
    List<PostDto> postDtos;
    int pageNo;
    int pageSize;
    int totalPages;
    int totalElements;
    boolean lastPage;
}
