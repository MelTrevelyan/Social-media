package com.meltrevelyan.socialmedia.subscription.service;

import com.meltrevelyan.socialmedia.exception.InvalidRequestException;
import com.meltrevelyan.socialmedia.exception.UserNotFoundException;
import com.meltrevelyan.socialmedia.post.dto.PostMapper;
import com.meltrevelyan.socialmedia.post.dto.PostOutDto;
import com.meltrevelyan.socialmedia.post.model.Post;
import com.meltrevelyan.socialmedia.post.repository.PostRepository;
import com.meltrevelyan.socialmedia.user.model.User;
import com.meltrevelyan.socialmedia.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class SubscriptionServiceImpl implements SubscriptionService {

    private final UserRepository userRepository;
    private final PostRepository postRepository;

    @Override
    public void addSubscription(Long requesterId, Long publisherId) {
        if (requesterId.equals(publisherId)) {
            throw new InvalidRequestException("The user cannot be subscribed to himself");
        }

        User requester = findRequester(requesterId);
        User publisher = findPublisher(publisherId);

        if (requester.getPublishers().contains(publisher)) {
            throw new InvalidRequestException("User is already subscribed to publisher");
        }

        requester.getFollowers().add(publisher);
        userRepository.save(requester);
        log.info("User with id {} subscribed to publisher with id {}", requesterId, publisherId);
    }

    @Override
    public void deleteSubscription(Long requesterId, Long publisherId) {
        if (requesterId.equals(publisherId)) {
            throw new InvalidRequestException("The user cannot be subscribed to himself");
        }

        User requester = findRequester(requesterId);
        User publisher = findPublisher(publisherId);

        if (!requester.getPublishers().contains(publisher)) {
            throw new InvalidRequestException("User is not subscribed to publisher");
        }

        requester.getFollowers().remove(publisher);
        userRepository.save(requester);
        log.info("User with id {} unsubscribed from publisher with id {}", requesterId, publisherId);
    }

    @Override
    public List<PostOutDto> getFeed(Long userId, Pageable pageable) {
        User user = findRequester(userId);
        List<Long> authors = user.getPublishers().stream()
                .map(User::getId)
                .collect(Collectors.toList());
        List<Post> posts = postRepository.findByAuthorIdInOrderByCreatedAtDesc(authors, pageable);

        log.info("Found feed for user with id {}", userId);
        return posts.stream()
                .map(PostMapper::toOutDto)
                .collect(Collectors.toList());
    }

    private User findRequester(Long requesterId) {
        return userRepository.findById(requesterId)
                .orElseThrow(() -> new UserNotFoundException("Requester was not found"));
    }

    private User findPublisher(Long publisherId) {
        return userRepository.findById(publisherId)
                .orElseThrow(() -> new UserNotFoundException("Publisher was not found"));
    }
}
