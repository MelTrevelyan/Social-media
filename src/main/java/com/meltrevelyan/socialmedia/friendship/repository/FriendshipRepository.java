package com.meltrevelyan.socialmedia.friendship.repository;

import com.meltrevelyan.socialmedia.friendship.model.Friendship;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FriendshipRepository extends JpaRepository<Friendship, Long> {
}
