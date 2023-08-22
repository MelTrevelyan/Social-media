package com.meltrevelyan.socialmedia.post;

import com.meltrevelyan.socialmedia.post.dto.NewPostDto;
import com.meltrevelyan.socialmedia.post.dto.PostMapper;
import com.meltrevelyan.socialmedia.post.dto.PostOutDto;
import com.meltrevelyan.socialmedia.post.model.Post;
import com.meltrevelyan.socialmedia.user.model.User;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PostMapperTest {

    @Test
    void toPostTest() {
        NewPostDto newPostDto = NewPostDto.builder()
                .heading("heading")
                .text("text")
                .image(new byte[]{})
                .build();

        Post post = PostMapper.toPost(newPostDto);

        assertEquals(newPostDto.getHeading(), post.getHeading());
        assertEquals(newPostDto.getText(), post.getText());
    }

    @Test
    void toOutDtoTest() {
        User author = User.builder()
                .id(1L)
                .build();

        Post post = Post.builder()
                .id(1L)
                .heading("heading")
                .text("text")
                .createdAt(LocalDateTime.now())
                .author(author)
                .build();

        PostOutDto result = PostMapper.toOutDto(post);

        assertEquals(post.getId(), result.getId());
        assertEquals(post.getText(), result.getText());
        assertEquals(post.getHeading(), result.getHeading());
        assertEquals(post.getAuthor().getId(), result.getAuthorId());
    }
}
