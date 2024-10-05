package com.personal.blog_app.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserDto {
    private int id;

    @NotEmpty
    @Size(max = 100)
    private String name;

    @Email(message = "Enter valid email")
    @Size(max = 100)
    private String email;

    @NotEmpty
    @Size(max = 20)
    private String password;

    @Size(max = 255)
    private String about;
}
