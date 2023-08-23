package com.meltrevelyan.socialmedia.friendship;

import com.meltrevelyan.socialmedia.exception.InvalidRequestException;
import com.meltrevelyan.socialmedia.exception.UserNotFoundException;
import com.meltrevelyan.socialmedia.friendship.dto.FriendshipDto;
import com.meltrevelyan.socialmedia.friendship.dto.FriendshipMapper;
import com.meltrevelyan.socialmedia.friendship.model.Friendship;
import com.meltrevelyan.socialmedia.friendship.model.FriendshipStatus;
import com.meltrevelyan.socialmedia.friendship.repository.FriendshipRepository;
import com.meltrevelyan.socialmedia.friendship.service.FriendshipServiceImpl;
import com.meltrevelyan.socialmedia.subscription.service.SubscriptionService;
import com.meltrevelyan.socialmedia.user.model.User;
import com.meltrevelyan.socialmedia.user.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class FriendshipServiceImplTest {

    @InjectMocks
    private FriendshipServiceImpl friendshipService;
    @Mock
    private FriendshipRepository friendshipRepository;
    @Mock
    private SubscriptionService subscriptionService;
    @Mock
    private UserService userService;
    private User requester;
    private User receiver;
    private Friendship friendship;

    @BeforeEach
    void initialize() {
        requester = User.builder()
                .id(1L)
                .email("lily@mail.ru")
                .username("lily")
                .password("password")
                .followers(new HashSet<>())
                .publishers(new HashSet<>())
                .build();

        receiver = User.builder()
                .id(2L)
                .email("jily@mail.ru")
                .username("jily")
                .password("password")
                .followers(new HashSet<>())
                .publishers(new HashSet<>())
                .build();

        friendship = new Friendship(1L, requester, receiver, FriendshipStatus.WAITING);
    }

    @Test
    void createFriendshipRequestFailRequesterNotFound() {
        when(userService.findUserById(anyLong())).thenThrow(new UserNotFoundException(""));

        assertThrows(UserNotFoundException.class, () -> friendshipService.createFriendshipRequest(1L, 2L));
    }

    @Test
    void createFriendshipRequestSuccessful() {
        when(userService.findUserById(1L)).thenReturn(requester);
        when(userService.findUserById(2L)).thenReturn(receiver);
        when(friendshipRepository.save(any(Friendship.class))).thenReturn(friendship);

        FriendshipDto result = friendshipService.createFriendshipRequest(1L, 2L);

        FriendshipDto expected = FriendshipMapper.toFriendshipDto(friendship);
        assertEquals(expected, result);
    }

    @Test
    void removeFromFriendsFailRequesterIsReceiver() {
        assertThrows(InvalidRequestException.class, () -> friendshipService
                .removeFromFriends(1L, 1L, 1L));
    }

    @Test
    void removeFromFriendsSuccessful() {
        when(userService.findUserById(1L)).thenReturn(requester);
        when(userService.findUserById(2L)).thenReturn(receiver);
        when(friendshipRepository.findById(anyLong())).thenReturn(Optional.of(friendship));

        friendshipService.removeFromFriends(1L, 2L, 1L);

        verify(friendshipRepository).save(friendship);
    }
}
