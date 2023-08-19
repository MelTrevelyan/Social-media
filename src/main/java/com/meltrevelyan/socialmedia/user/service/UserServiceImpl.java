package com.meltrevelyan.socialmedia.user.service;

import com.meltrevelyan.socialmedia.exception.UserNotFoundException;
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

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserOutDto addUser(UserRegistrationDto registrationDto) {
        User user = UserMapper.toUser(registrationDto);
        user.setPassword(passwordEncoder.encode(registrationDto.getPassword()));
        log.info("Adding new user with email {} and username {}", user.getEmail(), user.getUsername());
        userRepository.save(user);
        return UserMapper.toOutDto(user);
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
    public UserOutDto addFriendshipRequest(Long userId, Long friendId) {
        return null;
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(String.format("User '%s' was not found", username)));
    }

    @Override
    public boolean existsByUsername(String username) {
        return userRepository.findByUsername(username).isPresent();
    }
}
