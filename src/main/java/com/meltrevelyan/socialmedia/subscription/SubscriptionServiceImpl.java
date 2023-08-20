package com.meltrevelyan.socialmedia.subscription;

import com.meltrevelyan.socialmedia.exception.InvalidRequestException;
import com.meltrevelyan.socialmedia.exception.UserNotFoundException;
import com.meltrevelyan.socialmedia.user.dto.UserMapper;
import com.meltrevelyan.socialmedia.user.dto.UserWithFollowersDto;
import com.meltrevelyan.socialmedia.user.model.User;
import com.meltrevelyan.socialmedia.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class SubscriptionServiceImpl implements SubscriptionService {

    private final UserRepository userRepository;

    @Override
    public UserWithFollowersDto addSubscription(Long requesterId, Long publisherId) {
        if (requesterId.equals(publisherId)) {
            throw new InvalidRequestException("The user cannot be subscribed to himself");
        }

        User requester = findRequester(requesterId);
        User publisher = findPublisher(publisherId);

        if (requester.getFollowers().contains(publisher)) {
            throw new InvalidRequestException("User is already subscribed to publisher");
        }

        requester.getFollowers().add(publisher);
        userRepository.save(requester);
        log.info("User with id {} subscribed to publisher with id {}", requesterId, publisherId);
        return UserMapper.toUserWithFollowersDto(requester);
    }

    @Override
    public UserWithFollowersDto deleteSubscription(Long requesterId, Long publisherId) {
        if (requesterId.equals(publisherId)) {
            throw new InvalidRequestException("The user cannot be subscribed to himself");
        }

        User requester = findRequester(requesterId);
        User publisher = findPublisher(publisherId);

        if (!requester.getFollowers().contains(publisher)) {
            throw new InvalidRequestException("User is not subscribed to publisher");
        }

        requester.getFollowers().remove(publisher);
        userRepository.save(requester);
        log.info("User with id {} unsubscribed from publisher with id {}", requesterId, publisherId);
        return UserMapper.toUserWithFollowersDto(requester);
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
