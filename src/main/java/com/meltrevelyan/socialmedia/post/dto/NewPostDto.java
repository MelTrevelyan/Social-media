package com.meltrevelyan.socialmedia.post.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NewPostDto {

    @NotEmpty
    @Size(min = 3, max = 150, message = "Heading size must be between 3 and 150 symbols")
    private String heading;
    @NotEmpty
    @Size(min = 3, max = 7000, message = "Text size must be between 3 and 7000 symbols")
    private String text;
    private byte[] image;
}
