package com.meltrevelyan.socialmedia.friendship.service;

import com.meltrevelyan.socialmedia.exception.AccessForbiddenException;
import com.meltrevelyan.socialmedia.exception.FriendshipNotFoundException;
import com.meltrevelyan.socialmedia.exception.InvalidRequestException;
import com.meltrevelyan.socialmedia.friendship.dto.FriendshipDto;
import com.meltrevelyan.socialmedia.friendship.dto.FriendshipMapper;
import com.meltrevelyan.socialmedia.friendship.model.Friendship;
import com.meltrevelyan.socialmedia.friendship.model.FriendshipStatus;
import com.meltrevelyan.socialmedia.friendship.repository.FriendshipRepository;
import com.meltrevelyan.socialmedia.subscription.SubscriptionService;
import com.meltrevelyan.socialmedia.user.model.User;
import com.meltrevelyan.socialmedia.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class FriendshipServiceImpl implements FriendshipService {

    private final FriendshipRepository friendshipRepository;
    private final SubscriptionService subscriptionService;
    private final UserService userService;

    @Override
    public FriendshipDto createFriendshipRequest(Long requesterId, Long receiverId) {
        checkRequesterIsNotReceiver(requesterId, receiverId);
        User requester = userService.findUserById(requesterId);
        User receiver = userService.findUserById(receiverId);

        Friendship friendship = FriendshipMapper.toFriendship(requester, receiver);
        subscriptionService.addSubscription(requesterId, receiverId);
        friendship = friendshipRepository.save(friendship);
        log.info("Added new friendship request from requester with id {} to receiver with id {}", requester, receiver);
        return FriendshipMapper.toFriendshipDto(friendship);
    }

    @Override
    public FriendshipDto setFriendshipApproval(Long userId, Long friendshipId, Boolean approved) {
        User receiver = userService.findUserById(userId);
        Friendship friendship = findFriendshipById(friendshipId);

        checkUserIsFriendshipReceiver(friendship, userId);
        checkFriendshipStatus(friendship);

        if (approved) {
            friendship.setStatus(FriendshipStatus.APPROVED);
            subscriptionService.addSubscription(userId, friendship.getRequester().getId());
            log.info("User with id {} has approved friendship request from user with id {}",
                    userId, friendship.getRequester().getId());
        } else {
            friendship.setStatus(FriendshipStatus.REJECTED);
            log.info("User with id {} has rejected friendship request from user with id {}",
                    userId, friendship.getRequester().getId());
        }
        return FriendshipMapper.toFriendshipDto(friendshipRepository.save(friendship));
    }

    @Override
    public void removeFromFriends(Long initiatorId, Long unfriendedUserId, Long friendshipId) {
        checkRequesterIsNotReceiver(initiatorId, unfriendedUserId);

        User initiator = userService.findUserById(initiatorId);
        User unfriended = userService.findUserById(unfriendedUserId);
        Friendship friendship = findFriendshipById(friendshipId);

        friendship.setStatus(FriendshipStatus.CANCELLED);
        subscriptionService.deleteSubscription(initiatorId, unfriendedUserId);
        log.info("User with id {} removed from friends user with id {}", initiatorId, unfriendedUserId);
        friendshipRepository.save(friendship);
    }

    private Friendship findFriendshipById(Long friendshipId) {
        return friendshipRepository.findById(friendshipId)
                .orElseThrow(() -> new FriendshipNotFoundException("Unable to set approval to a non-existent friendship"));
    }

    private void checkRequesterIsNotReceiver(Long requesterId, Long receiverId) {
        if (requesterId.equals(receiverId)) {
            throw new InvalidRequestException("Requester and receiver must not be the same person");
        }
    }

    private void checkUserIsFriendshipReceiver(Friendship friendship, Long userId) {
        if (!friendship.getReceiver().getId().equals(userId)) {
            throw new AccessForbiddenException("Only friendship request receiver is able to set approval");
        }
    }

    private void checkFriendshipStatus(Friendship friendship) {
        if (!FriendshipStatus.WAITING.equals(friendship.getStatus())) {
            throw new InvalidRequestException("Unable to set the approval to friendship without status WAITING");
        }
    }
}
