package com.meltrevelyan.socialmedia.post;

import com.meltrevelyan.socialmedia.post.dto.NewPostDto;
import com.meltrevelyan.socialmedia.post.dto.PostMapper;
import com.meltrevelyan.socialmedia.post.dto.PostOutDto;
import com.meltrevelyan.socialmedia.post.dto.PostUpdateDto;
import com.meltrevelyan.socialmedia.post.model.Post;
import com.meltrevelyan.socialmedia.post.repository.PostRepository;
import com.meltrevelyan.socialmedia.post.service.PostService;
import com.meltrevelyan.socialmedia.user.model.User;
import com.meltrevelyan.socialmedia.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class PostServiceIntegrationTest {

    private final PostService postService;
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private User author;
    private User user;
    private Post post;

    @BeforeEach
    void initialize() {
        author = User.builder()
                .id(1L)
                .email("lily@mail.ru")
                .username("lily")
                .password("password")
                .followers(new HashSet<>())
                .publishers(new HashSet<>())
                .build();

        user = User.builder()
                .id(1L)
                .email("tulip@mail.ru")
                .username("tulip")
                .password("password")
                .followers(new HashSet<>())
                .publishers(new HashSet<>())
                .build();

        post = new Post(1L, "heading", "text", new byte[]{}, author, LocalDateTime.now());
    }

    @Test
    void getAllPostsWhenDbIsEmptyThenReturnEmptyList() {
        int from = 0;
        int size = 10;
        userRepository.save(author);
        List<PostOutDto> posts = postService.findNewPosts(user.getId(), from, size);

        assertEquals(0, posts.size());
    }

    @Test
    void getAllPostsReturnListOfPost() {
        int from = 0;
        int size = 10;
        userRepository.save(author);
        postRepository.save(post);

        List<PostOutDto> posts = postService.findNewPosts(user.getId(), from, size);

        assertEquals(1, posts.size());
    }

    @Test
    void addPostSuccessfulAndReturnOutDto() {
        NewPostDto newPostDto = new NewPostDto("heading", "text", new byte[]{});
        userRepository.save(author);

        PostOutDto result = postService.addNewPost(author.getId(), newPostDto);

        assertEquals(newPostDto.getText(), result.getText());
        assertEquals(newPostDto.getHeading(), result.getHeading());
    }

    @Test
    void findPostByIdSuccessfulAndReturnOutDto() {
        PostOutDto expected = PostMapper.toOutDto(post);
        userRepository.save(author);
        userRepository.save(user);
        postRepository.save(post);

        PostOutDto result = postService.findPostById(user.getId(), post.getId());

        assertEquals(expected, result);
        assertEquals(expected.getHeading(), result.getHeading());
        assertEquals(expected.getText(), result.getText());
    }

    @Test
    void updatePostSuccessfulAndReturnOutDto() {
        PostUpdateDto updateDto = new PostUpdateDto("heading update", "text update", null);

        userRepository.save(author);
        postRepository.save(post);

        PostOutDto result = postService.updatePost(author.getId(), post.getId(), updateDto);

        assertEquals("heading update", result.getHeading());
        assertEquals("text update", result.getText());
    }

    @Test
    void deletePostSuccessful() {
        userRepository.save(author);
        postRepository.save(post);

        postService.deletePost(author.getId(), post.getId());

        assertEquals(0, postRepository.findAll().size());
    }
}
