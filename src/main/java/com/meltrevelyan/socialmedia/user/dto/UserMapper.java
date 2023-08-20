package com.meltrevelyan.socialmedia.user.dto;

import com.meltrevelyan.socialmedia.user.model.User;
import lombok.experimental.UtilityClass;

import java.util.stream.Collectors;

@UtilityClass
public class UserMapper {

    public static User toUser(UserRegistrationDto registrationDto) {
        return User.builder()
                .username(registrationDto.getUsername())
                .email(registrationDto.getEmail())
                .build();
    }

    public static UserOutDto toOutDto(User user) {
        return UserOutDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .username(user.getUsername())
                .build();
    }

    public static UserWithFollowersDto toUserWithFollowersDto(User user) {
        UserWithFollowersDto dto = UserWithFollowersDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .username(user.getUsername())
                .build();

        dto.setFollowerIds(user.getFollowers().stream()
                .map(User::getId)
                .collect(Collectors.toList()));

        return dto;
    }
}
