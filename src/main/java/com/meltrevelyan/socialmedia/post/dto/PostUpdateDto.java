package com.meltrevelyan.socialmedia.post.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostUpdateDto {

    @Size(min = 3, max = 150, message = "Heading size must be between 3 and 150 symbols")
    private String heading;
    @Size(min = 3, max = 7000, message = "Text size must be between 3 and 7000 symbols")
    private String text;
    private byte[] image;
}
