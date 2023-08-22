package com.meltrevelyan.socialmedia.user;

import com.meltrevelyan.socialmedia.user.dto.UserOutDto;
import com.meltrevelyan.socialmedia.user.dto.UserRegistrationDto;
import com.meltrevelyan.socialmedia.user.model.User;
import com.meltrevelyan.socialmedia.user.repository.UserRepository;
import com.meltrevelyan.socialmedia.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class UserServiceIntegrationTest {

    private final UserService userService;
    private final UserRepository userRepository;

    @Test
    void getAllUsersWhenDbIsEmptyThenReturnEmptyList() {

        List<UserOutDto> result = userService.findAllUsers();

        assertTrue(result.isEmpty());
    }

    @Test
    void getAllUsersWhenDbHasTwoUsersThenReturnList() {
        User user1 = User.builder()
                .id(0L)
                .email("lily@mail.ru")
                .username("lily")
                .password("password")
                .build();
        User user2 = User.builder()
                .id(0L)
                .email("two@ru")
                .username("user2")
                .password("password")
                .build();
        userRepository.save(user1);
        userRepository.save(user2);

        List<UserOutDto> result = userService.findAllUsers();

        assertEquals(2, result.size());
        assertEquals(1, result.get(0).getId());
        assertEquals(2, result.get(1).getId());
    }

    @Test
    void createUserWhenUserHasSameEmailThenConflictException() {
        User user = User.builder()
                .id(0L)
                .email("lily@mail.ru")
                .username("lily")
                .password("password")
                .build();
        userRepository.save(user);
        UserRegistrationDto userDto = new UserRegistrationDto("lily@mail.ru", "lily", "password",
                "password");

        assertThrows(DataIntegrityViolationException.class, () -> userService.addUser(userDto));
    }

    @Test
    void createUserThenReturnUserDto() {
        UserRegistrationDto userDto = new UserRegistrationDto("lily@mail.ru", "lily", "password",
                "password");

        UserOutDto result = userService.addUser(userDto);

        assertEquals(1L, result.getId());
        assertEquals(userDto.getUsername(), result.getUsername());
        assertEquals(userDto.getEmail(), result.getEmail());
    }

    @Test
    void createUserWhenUserEmailNullThenDataIntegrityExceptionThrown() {
        UserRegistrationDto userDto = new UserRegistrationDto(null, "lily", "password",
                "password");

        assertThrows(DataIntegrityViolationException.class, () -> userService.addUser(userDto));
    }

    @Test
    void deleteUserThenBdIsEmpty() {
        User user = User.builder()
                .id(0L)
                .email("lily@mail.ru")
                .username("lily")
                .password("password")
                .build();

        userRepository.save(user);

        assertEquals(1, userRepository.findAll().size());

        userService.deleteUser(1L);

        assertTrue(userRepository.findAll().isEmpty());
    }

    @Test
    void getUserByIdThenReturnCorrectUserDto() {
        User userOld = User.builder()
                .id(0L)
                .email("lily@mail.ru")
                .username("lily")
                .password("password")
                .build();
        User user = User.builder()
                .id(0L)
                .email("rose@mail.ru")
                .username("rose")
                .password("password")
                .build();

        userRepository.save(userOld);
        userRepository.save(user);

        User user1 = userService.findUserById(1L);
        User user2 = userService.findUserById(2L);

        assertEquals(1L, user1.getId());
        assertEquals(userOld.getUsername(), user1.getUsername());
        assertEquals(userOld.getEmail(), user1.getEmail());
        assertEquals(2L, user2.getId());
        assertEquals(user.getUsername(), user2.getUsername());
        assertEquals(user.getEmail(), user2.getEmail());
    }
}
