package com.meltrevelyan.socialmedia.post.service;

import com.meltrevelyan.socialmedia.post.dto.NewPostDto;
import com.meltrevelyan.socialmedia.post.dto.PostOutDto;
import com.meltrevelyan.socialmedia.post.dto.PostUpdateDto;

import java.util.List;

public interface PostService {

    PostOutDto addNewPost(Long authorId, NewPostDto newPostDto);

    PostOutDto findPostById(Long userId, Long postId);

    List<PostOutDto> findNewPosts(Long userId, Integer from, Integer size);

    PostOutDto updatePost(Long userId, Long postId, PostUpdateDto updateDto);

    void deletePost(Long userId, Long postId);
}
