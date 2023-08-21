package com.meltrevelyan.socialmedia.friendship.dto;

import com.meltrevelyan.socialmedia.friendship.model.Friendship;
import com.meltrevelyan.socialmedia.friendship.model.FriendshipStatus;
import com.meltrevelyan.socialmedia.user.model.User;
import lombok.experimental.UtilityClass;

@UtilityClass
public class FriendshipMapper {

    public static Friendship toFriendship(User requester, User receiver) {
        return Friendship.builder()
                .requester(requester)
                .receiver(receiver)
                .status(FriendshipStatus.WAITING)
                .build();
    }

    public static FriendshipDto toFriendshipDto(Friendship friendship) {
        return new FriendshipDto(
                friendship.getId(),
                friendship.getRequester().getId(),
                friendship.getReceiver().getId(),
                friendship.getStatus());
    }
}
