package com.personal.blog_app.service;

import com.personal.blog_app.dto.UserDto;

import java.util.List;

public interface IUserService {

//    UserDto registerUser(UserDto userDto);

    UserDto createUser(UserDto userDto);

    UserDto updateUser(UserDto userDto, Integer userId);

    UserDto getUserById(Integer userId);

    List<UserDto> getAllUsers();

    UserDto deleteUser(Integer userId);
}
