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
    @Size(min = 3, max = 255, message = "Email size must be between 3 and 255 symbols")
    private String email;
    @NotEmpty
    @Size(min = 3, max = 255, message = "Name size must be between 3 and 255 symbols")
    private String username;
    @NotEmpty
    @Size(min = 3, max = 50, message = "Password size must be between 3 and 255 symbols")
    private String password;
    @NotEmpty
    @Size(min = 3, max = 50, message = "Password size must be between 3 and 255 symbols")
    private String confirmPassword;
}
