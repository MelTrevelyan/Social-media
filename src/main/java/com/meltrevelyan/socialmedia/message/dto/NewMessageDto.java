package com.meltrevelyan.socialmedia.message.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NewMessageDto {

    @NotEmpty
    @Size(min = 1, max = 7000, message = "Text size must be between 1 and 7000 symbols")
    private String text;
}
