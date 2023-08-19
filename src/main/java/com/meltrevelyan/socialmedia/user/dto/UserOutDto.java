package com.meltrevelyan.socialmedia.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserOutDto {

    private Long id;
    private String email;
    private String username;
}
