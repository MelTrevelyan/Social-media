package com.meltrevelyan.socialmedia.user.service;

import com.meltrevelyan.socialmedia.user.dto.UserOutDto;
import com.meltrevelyan.socialmedia.user.dto.UserRegistrationDto;
import com.meltrevelyan.socialmedia.user.model.User;

public interface UserService {

    UserOutDto addUser(UserRegistrationDto registrationDto);

    void deleteUser(Long userId);

    User findUserById(Long userId);

    UserOutDto addFriendshipRequest(Long userId, Long friendId);

    User findByUsername(String username);

    boolean existsByUsername(String username);
}
