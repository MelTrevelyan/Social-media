package com.meltrevelyan.socialmedia.post.dto;

import com.meltrevelyan.socialmedia.post.model.Post;
import lombok.experimental.UtilityClass;

import java.time.LocalDateTime;

@UtilityClass
public class PostMapper {

    public static Post toPost(NewPostDto newPostDto) {
        return Post.builder()
                .heading(newPostDto.getHeading())
                .text(newPostDto.getText())
                .image(newPostDto.getImage())
                .createdAt(LocalDateTime.now())
                .build();
    }

    public static PostOutDto toOutDto(Post post) {
        return PostOutDto.builder()
                .id(post.getId())
                .heading(post.getHeading())
                .text(post.getText())
                .authorId(post.getAuthor().getId())
                .build();
    }
}
