package com.meltrevelyan.socialmedia.subscription.service;

import com.meltrevelyan.socialmedia.post.dto.PostOutDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface SubscriptionService {

    void addSubscription(Long requesterId, Long publisherId);

    void deleteSubscription(Long requesterId, Long publisherId);

    List<PostOutDto> getFeed(Long userId, Pageable pageable);
}
