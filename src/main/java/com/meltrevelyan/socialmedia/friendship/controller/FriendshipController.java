package com.meltrevelyan.socialmedia.friendship.controller;

import com.meltrevelyan.socialmedia.friendship.dto.FriendshipDto;
import com.meltrevelyan.socialmedia.friendship.service.FriendshipService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/friends")
public class FriendshipController {

    private final FriendshipService friendshipService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public FriendshipDto createFriendshipRequest(@RequestHeader("X-Social-Media-User-Id") Long requesterId,
                                                 @Positive @RequestParam Long receiverId) {
        return friendshipService.createFriendshipRequest(requesterId, receiverId);
    }

    @PutMapping(value = "/{friendshipId}")
    public FriendshipDto setFriendshipApproval(@RequestHeader("X-Social-Media-User-Id") Long userId,
                                               @Positive @PathVariable Long friendshipId,
                                               @NotNull @RequestParam Boolean approved) {
        return friendshipService.setFriendshipApproval(userId, friendshipId, approved);
    }

    @DeleteMapping(value = "/{friendshipId}")
    public void removeFromFriends(@RequestHeader("X-Social-Media-User-Id") Long initiatorId,
                                  @Positive @RequestParam Long unfriendedUserId,
                                  @Positive @PathVariable Long friendshipId) {
        friendshipService.removeFromFriends(initiatorId, unfriendedUserId, friendshipId);
    }
}
