package com.meltrevelyan.socialmedia.message.controller;

import com.meltrevelyan.socialmedia.message.dto.MessageOutDto;
import com.meltrevelyan.socialmedia.message.dto.NewMessageDto;
import com.meltrevelyan.socialmedia.message.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.List;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/messages")
public class MessageController {

    private final MessageService messageService;

    @PostMapping("/{receiverId}")
    @ResponseStatus(HttpStatus.CREATED)
    public MessageOutDto addMessage(@RequestHeader("X-Social-Media-User-Id") Long senderId,
                                    @Positive @PathVariable Long receiverId,
                                    @Valid @RequestBody NewMessageDto newMessageDto) {
        return messageService.addMessage(senderId, receiverId, newMessageDto);
    }

    @GetMapping("/{senderId}")
    public List<MessageOutDto> getAllMessagesFromSender(@RequestHeader("X-Social-Media-User-Id") Long userId,
                                                        @Positive @PathVariable Long senderId) {
        return messageService.getAllMessagesFromSender(userId, senderId);
    }
}
