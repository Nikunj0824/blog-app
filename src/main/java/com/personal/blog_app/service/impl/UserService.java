package com.personal.blog_app.service.impl;

import com.personal.blog_app.dto.UserDto;
import com.personal.blog_app.entity.Role;
import com.personal.blog_app.entity.User;
import com.personal.blog_app.exeption.ResourceNotFoundException;
import com.personal.blog_app.repository.RoleRepo;
import com.personal.blog_app.repository.UserRepo;
import com.personal.blog_app.service.IUserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.personal.blog_app.util.DtoEntityMapper.dtoToEntity;
import static com.personal.blog_app.util.DtoEntityMapper.entityToDto;

@Service
public class UserService implements IUserService {

    private static final String ID = "id";
    private static final String USER = "user";

    private final UserRepo userRepo;
    private final RoleRepo roleRepo;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepo userRepo, RoleRepo roleRepo, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.roleRepo = roleRepo;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDto createUser(UserDto userDto) {
        User user = dtoToEntity(userDto, User.class);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // normal user-2
        // admin - 1
        Role role = roleRepo.findById(2).orElseThrow(() -> new ResourceNotFoundException("Role", "type", "GENERAL"));
        user.getRoles().add(role);

        User savedUser = userRepo.save(user);
        return entityToDto(savedUser, UserDto.class);
    }

//    @Override
//    public UserDto createUser(UserDto userDto) {
//        User savedUser = userRepo.save(dtoToEntity(userDto, User.class));
//        return entityToDto(savedUser, UserDto.class);
//    }


    @Override
    public UserDto updateUser(UserDto userDto, Integer userId) {
        User user = userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException(USER, ID, userId));
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        user.setAbout(userDto.getAbout());

        User savedUser = userRepo.save(user);
        return entityToDto(savedUser, UserDto.class);
    }

    @Override
    public UserDto getUserById(Integer userId) {
        User user = userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException(USER, ID, userId));
        return entityToDto(user, UserDto.class);
    }

    @Override
    public List<UserDto> getAllUsers() {
        List<User> users = userRepo.findAll();
        return users.stream().map(user -> entityToDto(user, UserDto.class)).toList();
    }

    @Override
    public UserDto deleteUser(Integer userId) {
        User user = userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException(USER, ID, userId));
        userRepo.deleteById(userId);
        return entityToDto(user, UserDto.class);
    }

}
