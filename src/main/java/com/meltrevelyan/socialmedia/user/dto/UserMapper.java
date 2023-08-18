package com.meltrevelyan.socialmedia.user.dto;

import com.meltrevelyan.socialmedia.user.model.User;
import lombok.experimental.UtilityClass;

@UtilityClass
public class UserMapper {

    public static User toUser(UserRegistrationDto registrationDto) {
        return User.builder()
                .username(registrationDto.getName())
                .email(registrationDto.getEmail())
                .build();
    }

    public static UserOutDto toOutDto(User user) {
        return UserOutDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .name(user.getUsername())
                .build();
    }
}
