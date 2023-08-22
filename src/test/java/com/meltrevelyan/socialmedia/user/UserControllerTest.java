package com.meltrevelyan.socialmedia.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.meltrevelyan.socialmedia.user.controller.UserController;
import com.meltrevelyan.socialmedia.user.dto.UserOutDto;
import com.meltrevelyan.socialmedia.user.service.UserService;
import com.meltrevelyan.socialmedia.util.JwtTokenUtil;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private UserService userService;
    @MockBean
    private JwtTokenUtil jwtTokenUtil;
    private UserOutDto outDto;

    @BeforeEach
    void setUp() {
        Long userId = 1L;

        outDto = new UserOutDto(userId, "lily@mail.ru", "lily");
    }

    @Test
    @WithMockUser
    @SneakyThrows
    void findAllUsersTest() {
        when(userService.findAllUsers()).thenReturn(List.of(outDto));

        String result = mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertEquals(objectMapper.writeValueAsString(List.of(outDto)), result);
    }

    @Test
    @WithMockUser
    @SneakyThrows
    void deleteUserFailInvalidUserId() {
        Long invalidId = -1L;

        mockMvc.perform(delete("/{userId}", invalidId))
                .andExpect(status().is4xxClientError());
    }
}
