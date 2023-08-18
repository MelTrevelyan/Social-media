package com.meltrevelyan.socialmedia.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRegistrationDto {

    @NotEmpty
    @Email
    @Size(min = 6, max = 255, message = "Email size must be between 6 and 255 symbols")
    private String email;

    @NotEmpty
    @Size(min = 6, max = 255, message = "Name size must be between 6 and 255 symbols")
    private String name;

    @NotEmpty
    @Size(min = 6, max = 50, message = "Password size must be between 6 and 255 symbols")
    private String password;
}
