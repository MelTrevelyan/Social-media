package com.meltrevelyan.socialmedia.post.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostOutDto {

    private Long id;
    private String heading;
    private String text;
    private Long authorId;
}
