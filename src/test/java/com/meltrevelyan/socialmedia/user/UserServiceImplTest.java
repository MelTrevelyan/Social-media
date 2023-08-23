package com.meltrevelyan.socialmedia.user;

import com.meltrevelyan.socialmedia.exception.UserNotFoundException;
import com.meltrevelyan.socialmedia.role.Role;
import com.meltrevelyan.socialmedia.role.RoleRepository;
import com.meltrevelyan.socialmedia.user.dto.UserOutDto;
import com.meltrevelyan.socialmedia.user.dto.UserRegistrationDto;
import com.meltrevelyan.socialmedia.user.model.User;
import com.meltrevelyan.socialmedia.user.repository.UserRepository;
import com.meltrevelyan.socialmedia.user.service.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private RoleRepository roleRepository;
    @InjectMocks
    private UserServiceImpl userService;
    private final User user = User.builder()
            .id(1L)
            .email("lily@mail.ru")
            .username("lily")
            .password("password")
            .roles(List.of(new Role()))
            .build();
    private final UserRegistrationDto  registrationDto = new UserRegistrationDto("lily@mail.ru", "lily",
            "password", "password");
    private final UserOutDto outDto = new UserOutDto(1L, "lily@mail.ru", "lily");

    @Test
    void findUserByIdWhenUserFoundThenReturnUserDto() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        User result = userService.findUserById(1L);

        assertEquals(user, result);
    }

    @Test
    void findUserByIdWhenUserNotFoundThenUserNotFoundExceptionThrown() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.findUserById(1L));
    }

    @Test
    void addUserThenReturnUserDto() {
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(passwordEncoder.encode(registrationDto.getPassword())).thenReturn(anyString());
        when(roleRepository.findByName("ROLE_USER")).thenReturn(new Role());

        UserOutDto result = userService.addUser(registrationDto);

        assertEquals(outDto, result);
    }

    @Test
    void findAllUsersThenReturnListOfUserDto() {
        when(userRepository.findAll()).thenReturn(List.of(user));

        List<UserOutDto> result = userService.findAllUsers();

        assertEquals(List.of(outDto), result);
    }

    @Test
    void existsByUsernameThenReturnTrue() {
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.of(user));

        boolean result = userService.existsByUsername(user.getUsername());

        assertTrue(result);
    }
}
