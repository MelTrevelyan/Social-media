package com.meltrevelyan.socialmedia.post.service;

import com.meltrevelyan.socialmedia.exception.AccessForbiddenException;
import com.meltrevelyan.socialmedia.exception.PostNotFoundException;
import com.meltrevelyan.socialmedia.post.dto.NewPostDto;
import com.meltrevelyan.socialmedia.post.dto.PostMapper;
import com.meltrevelyan.socialmedia.post.dto.PostOutDto;
import com.meltrevelyan.socialmedia.post.dto.PostUpdateDto;
import com.meltrevelyan.socialmedia.post.model.Post;
import com.meltrevelyan.socialmedia.post.repository.PostRepository;
import com.meltrevelyan.socialmedia.user.model.User;
import com.meltrevelyan.socialmedia.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final UserService userService;

    @Override
    @Transactional
    public PostOutDto addNewPost(Long authorId, NewPostDto newPostDto) {
        User user = checkUser(authorId);
        Post post = PostMapper.toPost(newPostDto);
        post.setAuthor(user);
        log.info("Adding new post by user with id {}", authorId);
        return PostMapper.toOutDto(postRepository.save(post));
    }

    @Override
    public PostOutDto findPostById(Long userId, Long postId) {
        checkUser(userId);
        log.info("Finding post by id {}", postId);
        return PostMapper.toOutDto(getPostById(postId));
    }

    @Override
    public List<PostOutDto> findNewPosts(Long userId, Integer from, Integer size) {
        checkUser(userId);
        Pageable pageable = PageRequest.of(from / size, size, Sort.by("createdAt").descending());
        List<Post> posts = postRepository.findAll(pageable).getContent();
        log.info("Finding new posts for user with id {}", userId);
        return posts.stream()
                .map(PostMapper::toOutDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public PostOutDto updatePost(Long userId, Long postId, PostUpdateDto updateDto) {
        checkUser(userId);
        Post post = getPostById(postId);
        checkAuthor(userId, post);

        if (updateDto.getHeading() != null) {
            post.setHeading(updateDto.getHeading());
        }
        if (updateDto.getText() != null) {
            post.setText(updateDto.getText());
        }
        if (updateDto.getImage() != null) {
            post.setImage(updateDto.getImage());
        }
        log.info("Post with id {} was updated", postId);
        return PostMapper.toOutDto(postRepository.save(post));
    }

    @Override
    @Transactional
    public void deletePost(Long authorId, Long postId) {
        checkUser(authorId);
        Post post = getPostById(postId);
        checkAuthor(authorId, post);
        postRepository.deleteById(postId);
        log.info("Post with id {} was deleted", postId);
    }

    private Post getPostById(Long postId) {
        return postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException(String.format("Post with id %d was not found", postId)));
    }

    private User checkUser(Long userId) {
        return userService.findUserById(userId);
    }

    private void checkAuthor(Long userId, Post post) {
        if (!post.getAuthor().getId().equals(userId)) {
            throw new AccessForbiddenException("Only an author of the post is allowed to do the operation");
        }
    }
}
