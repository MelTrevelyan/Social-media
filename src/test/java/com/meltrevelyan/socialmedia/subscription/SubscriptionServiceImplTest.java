package com.meltrevelyan.socialmedia.subscription;

import com.meltrevelyan.socialmedia.exception.InvalidRequestException;
import com.meltrevelyan.socialmedia.exception.UserNotFoundException;
import com.meltrevelyan.socialmedia.post.dto.PostMapper;
import com.meltrevelyan.socialmedia.post.dto.PostOutDto;
import com.meltrevelyan.socialmedia.post.model.Post;
import com.meltrevelyan.socialmedia.post.repository.PostRepository;
import com.meltrevelyan.socialmedia.subscription.service.SubscriptionServiceImpl;
import com.meltrevelyan.socialmedia.user.model.User;
import com.meltrevelyan.socialmedia.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SubscriptionServiceImplTest {

    @InjectMocks
    private SubscriptionServiceImpl subscriptionService;
    @Mock
    private UserRepository userRepository;
    @Mock
    private PostRepository postRepository;
    private User requester;
    private User publisher;

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

        publisher = User.builder()
                .id(2L)
                .email("jily@mail.ru")
                .username("jily")
                .password("password")
                .followers(new HashSet<>())
                .publishers(new HashSet<>())
                .build();
    }

    @Test
    void addSubscriptionFailRequesterIsPublisher() {
        assertThrows(InvalidRequestException.class, () -> subscriptionService.addSubscription(1L, 1L));
    }

    @Test
    void addSubscriptionFailUserAlreadySubscribed() {
        User user = requester;
        user.getPublishers().add(publisher);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userRepository.findById(2L)).thenReturn(Optional.of(publisher));

        assertThrows(InvalidRequestException.class, () -> subscriptionService.addSubscription(1L, 2L));
    }

    @Test
    void addSubscriptionSuccessful() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(requester));
        when(userRepository.findById(2L)).thenReturn(Optional.of(publisher));

        subscriptionService.addSubscription(1L, 2L);

        verify(userRepository).save(requester);
    }

    @Test
    void deleteSubscriptionFailRequesterIsPublisher() {
        assertThrows(InvalidRequestException.class, () -> subscriptionService.deleteSubscription(1L, 1L));
    }

    @Test
    void deleteSubscriptionFailUserNotSubscribed() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(requester));
        when(userRepository.findById(2L)).thenReturn(Optional.of(publisher));

        assertThrows(InvalidRequestException.class, () -> subscriptionService.deleteSubscription(1L, 2L));
    }

    @Test
    void deleteSubscriptionSuccessful() {
        User user = requester;
        user.getPublishers().add(publisher);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userRepository.findById(2L)).thenReturn(Optional.of(publisher));

        subscriptionService.deleteSubscription(1L, 2L);

        verify(userRepository).save(requester);
    }

    @Test
    void getFeedFailUserNotFound() {
        Pageable pageable = PageRequest.of(0, 10);
        when(userRepository.findById(anyLong())).thenThrow(new UserNotFoundException(""));

        assertThrows(UserNotFoundException.class, () -> subscriptionService.getFeed(1L, pageable));
    }

    @Test
    void getFeedSuccessful() {
        Pageable pageable = PageRequest.of(0, 10);
        Post post = new Post(1L, "heading", "text", new byte[]{}, publisher, LocalDateTime.now());
        requester.getPublishers().add(publisher);
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(requester));
        when(postRepository.findByAuthorIdInOrderByCreatedAtDesc(List.of(2L), pageable))
                .thenReturn(List.of(post));

        List<PostOutDto> result = subscriptionService.getFeed(1L, pageable);

        List<PostOutDto> expected = List.of(PostMapper.toOutDto(post));
        assertEquals(expected, result);
    }
}
