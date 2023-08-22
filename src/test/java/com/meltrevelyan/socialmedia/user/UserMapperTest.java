package com.meltrevelyan.socialmedia.user;

import com.meltrevelyan.socialmedia.user.dto.UserMapper;
import com.meltrevelyan.socialmedia.user.dto.UserOutDto;
import com.meltrevelyan.socialmedia.user.dto.UserRegistrationDto;
import com.meltrevelyan.socialmedia.user.model.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserMapperTest {

    @Test
    public void toUserTest() {
        UserRegistrationDto registrationDto = new UserRegistrationDto("lily@mail.ru", "lily",
                "password", "password");

        User user = UserMapper.toUser(registrationDto);

        assertEquals(registrationDto.getEmail(), user.getEmail());
        assertEquals(registrationDto.getUsername(), user.getUsername());
    }

    @Test
    public void toOutDtoTest() {
        User user = User.builder()
                .id(1L)
                .email("lily@mail.ru")
                .username("lily")
                .password("password")
                .build();

        UserOutDto outDto = UserMapper.toOutDto(user);

        assertEquals(user.getId(), outDto.getId());
        assertEquals(user.getEmail(), outDto.getEmail());
        assertEquals(user.getUsername(), outDto.getUsername());
    }
}
