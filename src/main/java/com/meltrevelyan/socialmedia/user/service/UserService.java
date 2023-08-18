package com.meltrevelyan.socialmedia.user.service;

import com.meltrevelyan.socialmedia.user.dto.UserOutDto;
import com.meltrevelyan.socialmedia.user.dto.UserRegistrationDto;

public interface UserService {

    UserOutDto addUser(UserRegistrationDto registrationDto);

    void deleteUser(Long userId);

    UserOutDto addFriendshipRequest(Long userId, Long friendId);
}
