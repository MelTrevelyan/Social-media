package com.meltrevelyan.socialmedia.subscription;

import com.meltrevelyan.socialmedia.user.dto.UserWithFollowersDto;

public interface SubscriptionService {

    UserWithFollowersDto addSubscription(Long requesterId, Long publisherId);

    UserWithFollowersDto deleteSubscription(Long requesterId, Long publisherId);
}
