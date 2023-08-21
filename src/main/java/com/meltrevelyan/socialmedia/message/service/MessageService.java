package com.meltrevelyan.socialmedia.message.service;

import com.meltrevelyan.socialmedia.message.dto.MessageOutDto;
import com.meltrevelyan.socialmedia.message.dto.NewMessageDto;

import java.util.List;

public interface MessageService {

    MessageOutDto addMessage(Long senderId, Long receiverId, NewMessageDto newMessageDto);

    List<MessageOutDto> getAllMessagesFromSender(Long userId, Long senderId);
}
