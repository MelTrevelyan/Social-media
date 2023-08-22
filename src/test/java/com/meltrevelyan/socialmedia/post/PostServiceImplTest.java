package com.meltrevelyan.socialmedia.post;

import com.meltrevelyan.socialmedia.exception.UserNotFoundException;
import com.meltrevelyan.socialmedia.post.dto.NewPostDto;
import com.meltrevelyan.socialmedia.post.dto.PostOutDto;
import com.meltrevelyan.socialmedia.post.dto.PostUpdateDto;
import com.meltrevelyan.socialmedia.post.model.Post;
import com.meltrevelyan.socialmedia.post.repository.PostRepository;
import com.meltrevelyan.socialmedia.post.service.PostServiceImpl;
import com.meltrevelyan.socialmedia.user.model.User;
import com.meltrevelyan.socialmedia.user.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PostServiceImplTest {

    @Mock
    private PostRepository postRepository;
    @InjectMocks
    private PostServiceImpl postService;
    @Mock
    private UserService userService;
    private User author;
    private NewPostDto newPostDto;
    private PostOutDto outDto;
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

        newPostDto = new NewPostDto("heading", "text", new byte[]{});

        outDto = new PostOutDto(1L, "heading", "text", 1L);

        post = new Post(1L, "heading", "text", new byte[]{}, author, LocalDateTime.now());
    }

    @Test
    void createPostWhenUserNotFoundThenThrowUserNotFoundException() {
        when(userService.findUserById(anyLong())).thenThrow(new UserNotFoundException(""));

        assertThrows(UserNotFoundException.class, () -> postService.addNewPost(author.getId(), newPostDto));
    }

    @Test
    void createPostThenReturnPostOutDto() {
        when(userService.findUserById(anyLong())).thenReturn(author);
        when(postRepository.save(any(Post.class))).thenReturn(post);

        PostOutDto result = postService.addNewPost(author.getId(), newPostDto);

        verify(postRepository).save(any(Post.class));
    }

    @Test
    void findPostByIdWhenUserNotFoundThenThrowUserNotFoundException() {
        when(userService.findUserById(anyLong())).thenThrow(new UserNotFoundException(""));

        assertThrows(UserNotFoundException.class, () -> postService.findPostById(author.getId(), post.getId()));
    }

    @Test
    void findPostByIdThenReturnPostOutDto() {
        when(userService.findUserById(anyLong())).thenReturn(author);
        when(postRepository.findById(anyLong())).thenReturn(Optional.of(post));

        PostOutDto result = postService.findPostById(author.getId(), post.getId());

        assertEquals(outDto, result);
    }

    @Test
    void updatePostThenReturnPostOutDto() {
        PostUpdateDto updateDto = new PostUpdateDto();
        updateDto.setText("update");
        Post updated = post;
        updated.setText("update");

        when(userService.findUserById(anyLong())).thenReturn(author);
        when(postRepository.findById(anyLong())).thenReturn(Optional.of(post));
        when(postRepository.save(any(Post.class))).thenReturn(updated);

        PostOutDto result = postService.updatePost(author.getId(), post.getId(), updateDto);

        assertEquals("update", result.getText());
    }

    @Test
    void deletePostTest() {
        when(userService.findUserById(anyLong())).thenReturn(author);
        when(postRepository.findById(anyLong())).thenReturn(Optional.of(post));

        postService.deletePost(author.getId(), post.getId());

        verify(postRepository).deleteById(post.getId());
    }
}
