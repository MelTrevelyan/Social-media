package com.meltrevelyan.socialmedia.message.dto;

import com.meltrevelyan.socialmedia.message.model.Message;
import com.meltrevelyan.socialmedia.user.model.User;
import lombok.experimental.UtilityClass;

import java.time.LocalDateTime;

@UtilityClass
public class MessageMapper {

    public static Message toMessage(NewMessageDto newMessageDto, User sender, User receiver) {
        return Message.builder()
                .text(newMessageDto.getText())
                .createdAt(LocalDateTime.now())
                .sender(sender)
                .receiver(receiver)
                .build();
    }

    public static MessageOutDto toOutDto(Message message) {
        return MessageOutDto.builder()
                .id(message.getId())
                .text(message.getText())
                .createdAt(message.getCreatedAt())
                .senderId(message.getSender().getId())
                .receiverId(message.getReceiver().getId())
                .build();
    }
}
