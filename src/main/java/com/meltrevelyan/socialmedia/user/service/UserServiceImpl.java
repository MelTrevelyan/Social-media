package com.meltrevelyan.socialmedia.user.service;

import com.meltrevelyan.socialmedia.exception.UserNotFoundException;
import com.meltrevelyan.socialmedia.role.Role;
import com.meltrevelyan.socialmedia.role.RoleRepository;
import com.meltrevelyan.socialmedia.user.dto.UserMapper;
import com.meltrevelyan.socialmedia.user.dto.UserOutDto;
import com.meltrevelyan.socialmedia.user.dto.UserRegistrationDto;
import com.meltrevelyan.socialmedia.user.model.User;
import com.meltrevelyan.socialmedia.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserOutDto addUser(UserRegistrationDto registrationDto) {
        User user = UserMapper.toUser(registrationDto);
        List<Role> roles = List.of(roleRepository.findByName("ROLE_USER"));
        user.setPassword(passwordEncoder.encode(registrationDto.getPassword()));
        user.setRoles(roles);
        log.info("Adding new user with email {} and username {}", user.getEmail(), user.getUsername());
        return UserMapper.toOutDto(userRepository.save(user));
    }

    @Override
    public void deleteUser(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new UserNotFoundException(String.format("User with id %d was not found", userId));
        }
        userRepository.deleteById(userId);
        log.info("User with id {} was deleted", userId);
    }

    @Override
    public List<UserOutDto> findAllUsers() {
        log.info("Finding all users");
        return userRepository.findAll().stream()
                .map(UserMapper::toOutDto)
                .collect(Collectors.toList());
    }

    @Override
    public User findUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(String.format("User with id %d was not found", userId)));
    }

    @Override
    public boolean existsByUsername(String username) {
        return userRepository.findByUsername(username).isPresent();
    }
}
