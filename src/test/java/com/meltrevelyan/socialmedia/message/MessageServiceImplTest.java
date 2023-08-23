package com.meltrevelyan.socialmedia.message;

import com.meltrevelyan.socialmedia.exception.UserNotFoundException;
import com.meltrevelyan.socialmedia.message.dto.MessageMapper;
import com.meltrevelyan.socialmedia.message.dto.MessageOutDto;
import com.meltrevelyan.socialmedia.message.dto.NewMessageDto;
import com.meltrevelyan.socialmedia.message.model.Message;
import com.meltrevelyan.socialmedia.message.repository.MessageRepository;
import com.meltrevelyan.socialmedia.message.service.MessageServiceImpl;
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
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MessageServiceImplTest {

    @InjectMocks
    private MessageServiceImpl messageService;
    @Mock
    private MessageRepository messageRepository;
    @Mock
    private UserService userService;
    private User sender;
    private User receiver;
    private NewMessageDto newMessageDto;
    private Message message;

    @BeforeEach
    void initialize() {
        sender = User.builder()
                .id(1L)
                .email("lily@mail.ru")
                .username("lily")
                .password("password")
                .followers(new HashSet<>())
                .publishers(new HashSet<>())
                .build();

        receiver = User.builder()
                .id(2L)
                .email("jily@mail.ru")
                .username("jily")
                .password("password")
                .followers(new HashSet<>())
                .publishers(new HashSet<>())
                .build();

        newMessageDto = new NewMessageDto("message");
        message = new Message(1L, sender, receiver, "message", LocalDateTime.now());
    }

    @Test
    void addMessageFailUserNotFound() {
        when(userService.findUserById(anyLong())).thenThrow(new UserNotFoundException(""));

        assertThrows(UserNotFoundException.class, () -> messageService.addMessage(1L, 2L, newMessageDto));
    }

    @Test
    void addMessageSuccessful() {
        when(userService.findUserById(1L)).thenReturn(sender);
        when(userService.findUserById(2L)).thenReturn(receiver);
        when(messageRepository.save(any(Message.class))).thenReturn(message);

        MessageOutDto result = messageService.addMessage(1L, 2L, newMessageDto);

        MessageOutDto expected = MessageMapper.toOutDto(message);
        assertEquals(expected, result);
    }

    @Test
    void getAllMessagesFromSenderFailUserNotFound() {
        when(userService.findUserById(anyLong())).thenThrow(new UserNotFoundException(""));

        assertThrows(UserNotFoundException.class, () -> messageService.getAllMessagesFromSender(1L, 2L));
    }

    @Test
    void getAllMessagesFromSenderSuccessful() {
        when(userService.findUserById(1L)).thenReturn(sender);
        when(userService.findUserById(2L)).thenReturn(receiver);
        when(messageRepository.findAllBySenderIdAndReceiverId(1L, 2L)).thenReturn(List.of(message));

        List<MessageOutDto> result = messageService.getAllMessagesFromSender(2L, 1L);

        List<MessageOutDto> expected = Stream.of(message).map(MessageMapper::toOutDto).collect(Collectors.toList());
        assertEquals(expected, result);
    }
}

