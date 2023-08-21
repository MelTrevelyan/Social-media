package com.meltrevelyan.socialmedia.friendship.service;

import com.meltrevelyan.socialmedia.friendship.dto.FriendshipDto;

public interface FriendshipService {

    FriendshipDto createFriendshipRequest(Long requesterId, Long receiverId);

    FriendshipDto setFriendshipApproval(Long userId, Long friendshipId, Boolean approved);

    void removeFromFriends(Long initiatorId, Long unfriendedUserId, Long friendshipId);
}
