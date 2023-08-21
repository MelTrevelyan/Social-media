package com.meltrevelyan.socialmedia.message.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MessageOutDto {

    private Long id;
    private Long senderId;
    private Long receiverId;
    private String text;
    private LocalDateTime createdAt;
}
