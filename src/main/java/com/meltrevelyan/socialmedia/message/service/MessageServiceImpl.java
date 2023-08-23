package com.meltrevelyan.socialmedia.message.service;

import com.meltrevelyan.socialmedia.message.dto.MessageMapper;
import com.meltrevelyan.socialmedia.message.dto.MessageOutDto;
import com.meltrevelyan.socialmedia.message.dto.NewMessageDto;
import com.meltrevelyan.socialmedia.message.model.Message;
import com.meltrevelyan.socialmedia.message.repository.MessageRepository;
import com.meltrevelyan.socialmedia.user.model.User;
import com.meltrevelyan.socialmedia.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;
    private final UserService userService;

    @Override
    public MessageOutDto addMessage(Long senderId, Long receiverId, NewMessageDto newMessageDto) {
        User sender = userService.findUserById(senderId);
        User receiver = userService.findUserById(receiverId);

        Message message = MessageMapper.toMessage(newMessageDto, sender, receiver);
        log.info("User with id {} sent a message to user with id {}", senderId, receiverId);

        return MessageMapper.toOutDto(messageRepository.save(message));
    }

    @Override
    public List<MessageOutDto> getAllMessagesFromSender(Long userId, Long senderId) {
        User user = userService.findUserById(userId);
        User sender = userService.findUserById(senderId);

        List<Message> messages = messageRepository.findAllBySenderIdAndReceiverId(senderId, userId);
        log.info("Finding messages from user with id {} to user with id {}", senderId, userId);
        return messages.stream()
                .map(MessageMapper::toOutDto)
                .collect(Collectors.toList());
    }
}
