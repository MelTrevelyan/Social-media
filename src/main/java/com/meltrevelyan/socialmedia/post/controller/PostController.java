package com.meltrevelyan.socialmedia.post.controller;

import com.meltrevelyan.socialmedia.post.dto.NewPostDto;
import com.meltrevelyan.socialmedia.post.dto.PostOutDto;
import com.meltrevelyan.socialmedia.post.dto.PostUpdateDto;
import com.meltrevelyan.socialmedia.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.io.IOException;
import java.util.List;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PostOutDto addNewPost(@Valid @RequestBody NewPostDto newPostDto,
                                 @RequestPart(required = false, name = "image") MultipartFile file,
                                 @RequestHeader("X-Social-Media-User-Id") Long authorId) throws IOException {
        if (file != null) {
            newPostDto.setImage(file.getBytes());
        }
        return postService.addNewPost(authorId, newPostDto);
    }

    @GetMapping("/{postId}")
    public PostOutDto findPostById(@RequestHeader("X-Social-Media-User-Id") Long userId,
                                   @Positive @PathVariable Long postId) {
        return postService.findPostById(userId, postId);
    }

    @GetMapping
    List<PostOutDto> findNewPosts(@RequestHeader("X-Social-Media-User-Id") Long userId,
                                  @PositiveOrZero @RequestParam(required = false, defaultValue = "0") Integer from,
                                  @Positive @RequestParam(required = false, defaultValue = "10") Integer size) {
        return postService.findNewPosts(userId, from, size);
    }

    @PatchMapping(value = "/{postId}")
    PostOutDto updatePost(@RequestHeader("X-Social-Media-User-Id") Long userId,
                          @RequestPart(required = false, name = "image") MultipartFile file,
                          @Positive @PathVariable Long postId,
                          @Valid @RequestBody PostUpdateDto updateDto) throws IOException {
        if (file != null) {
            updateDto.setImage(file.getBytes());
        }
        return postService.updatePost(userId, postId, updateDto);
    }

    @DeleteMapping(value = "/{postId}")
    public void deletePost(@RequestHeader("X-Social-Media-User-Id") Long userId,
                           @Positive @PathVariable Long postId) {
        postService.deletePost(userId, postId);
    }
}
