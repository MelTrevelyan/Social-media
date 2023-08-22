package com.meltrevelyan.socialmedia.user.service;

import com.meltrevelyan.socialmedia.user.dto.UserOutDto;
import com.meltrevelyan.socialmedia.user.dto.UserRegistrationDto;
import com.meltrevelyan.socialmedia.user.model.User;

import java.util.List;

public interface UserService {

    UserOutDto addUser(UserRegistrationDto registrationDto);

    void deleteUser(Long userId);

    List<UserOutDto> findAllUsers();

    User findUserById(Long userId);

    boolean existsByUsername(String username);
}
