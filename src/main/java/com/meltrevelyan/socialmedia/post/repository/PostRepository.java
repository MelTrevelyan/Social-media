package com.meltrevelyan.socialmedia.post.repository;

import com.meltrevelyan.socialmedia.post.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
}
