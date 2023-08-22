package com.meltrevelyan.socialmedia.post.repository;

import com.meltrevelyan.socialmedia.post.model.Post;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findByAuthorIdInOrderByCreatedAtDesc(List<Long> ids, Pageable pageable);
}
