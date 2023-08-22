package com.meltrevelyan.socialmedia.post;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.meltrevelyan.socialmedia.post.controller.PostController;
import com.meltrevelyan.socialmedia.post.dto.NewPostDto;
import com.meltrevelyan.socialmedia.post.dto.PostOutDto;
import com.meltrevelyan.socialmedia.post.service.PostService;
import com.meltrevelyan.socialmedia.user.model.User;
import com.meltrevelyan.socialmedia.util.JwtTokenUtil;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = PostController.class)
public class PostControllerTest {

    private MockMvc mvc;
    @Autowired
    private WebApplicationContext webApplicationContext;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private PostService postService;
    @MockBean
    private JwtTokenUtil jwtTokenUtil;
    private PostOutDto outDto;
    private NewPostDto newPostDto;
    private User author;
    private Long postId;

    private final LocalDateTime createdAt = LocalDateTime.now();

    private Pageable pageable;

    @BeforeEach
    void setUp() {
        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        postId = 1L;
        pageable = PageRequest.of(0, 10);

        author = User.builder()
                .id(1L)
                .email("tulip@mail.ru")
                .username("tulip")
                .password("password")
                .followers(new HashSet<>())
                .publishers(new HashSet<>())
                .build();

        newPostDto = new NewPostDto("post heading", "post text", new byte[]{});

        outDto = PostOutDto.builder()
                .id(1L)
                .heading("post heading")
                .text("post text")
                .authorId(1L)
                .build();
    }

//    @WithMockUser
//    @SneakyThrows
//    @Test
//    void testAddNewPostSuccessful() {
//        when(postService.addNewPost(author.getId(), newPostDto)).thenReturn(outDto);
//
//        mvc.perform(post("/posts")
//                        .header("X-Social-Media-User-Id", author.getId())
//                        .content(objectMapper.writeValueAsString(newPostDto))
//                        .accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isCreated());
//    }

//    @Test
//    @WithMockUser
//    @SneakyThrows
//    void testFindAllNewPostsTest() {
//        when(postService.findNewPosts(author.getId(), 0, 10))
//                .thenReturn(List.of(outDto));
//
//        mvc.perform(get("/posts")
//                        .param("from", "0")
//                        .param("size", "10")
//                        .accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$", hasSize(1)))
//                .andExpect(jsonPath("$[0].id", is(outDto.getId()), Long.class))
//                .andExpect(jsonPath("$[0].authorId", is(author.getId()), Long.class))
//                .andExpect(jsonPath("$[0].header", is(outDto.getHeading())))
//                .andExpect(jsonPath("$[0].description", is(outDto.getText())));
//    }
}
