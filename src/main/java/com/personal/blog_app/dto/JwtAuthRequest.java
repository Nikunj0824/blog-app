package com.personal.blog_app.dto;

import lombok.Data;

@Data
public class JwtAuthRequest {
    private String userName;
    private String password;
}
