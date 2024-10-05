package com.personal.blog_app.controller;

import com.personal.blog_app.dto.ApiResponseDto;
import com.personal.blog_app.dto.UserDto;
import com.personal.blog_app.service.impl.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.personal.blog_app.controller.constant.ApiConstant.*;

@RestController
@RequestMapping(API + USERS)
@Tag(name = "User Management", description = "User Management APIs")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(value = NEW, consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto) {
        UserDto createdUser = userService.createUser(userDto);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    @PutMapping(value = USER_ID, consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<UserDto> updateUser(@RequestBody UserDto userDto, @PathVariable int userId) {
        UserDto updatedUser = userService.updateUser(userDto, userId);
        return ResponseEntity.ok(updatedUser);
    }

    @GetMapping(value = USER_ID)
    ResponseEntity<UserDto> getUserById(@PathVariable int userId) {
        UserDto userDto = userService.getUserById(userId);
        return ResponseEntity.ok(userDto);
    }

    @GetMapping(value = ALL)
    ResponseEntity<List<UserDto>> getAllUsers() {
        List<UserDto> userDtos = userService.getAllUsers();
        return ResponseEntity.ok(userDtos);
    }

    @DeleteMapping(value = USER_ID)
    ResponseEntity<ApiResponseDto> deleteUser(@PathVariable int userId) {
        UserDto userDto = userService.deleteUser(userId);
        ApiResponseDto apiResponseDto = new ApiResponseDto(String.format("User: %s deleted successfully", userDto.getName()), true);
        return ResponseEntity.ok(apiResponseDto);
    }
}
