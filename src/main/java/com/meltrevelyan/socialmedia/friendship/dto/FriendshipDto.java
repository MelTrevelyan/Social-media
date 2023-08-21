package com.meltrevelyan.socialmedia.friendship.dto;

import com.meltrevelyan.socialmedia.friendship.model.FriendshipStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FriendshipDto {

    private Long id;
    private Long requesterId;
    private Long receiverId;
    private FriendshipStatus status;
}
