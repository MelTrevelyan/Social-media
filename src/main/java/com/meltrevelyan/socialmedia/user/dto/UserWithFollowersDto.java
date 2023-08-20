package com.meltrevelyan.socialmedia.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserWithFollowersDto {

    private Long id;
    private String email;
    private String username;
    List<Long> followerIds;
}
