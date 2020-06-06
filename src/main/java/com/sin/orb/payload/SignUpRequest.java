package com.sin.orb.payload;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
public class SignUpRequest {
    @NotBlank
    @Size(min = 4, max = 16)
    private String username;

    @Email
    @NotBlank
    @Size(min = 1, max = 129)
    private String email;

    @NotBlank
    @Size(min = 1, max = 128)
    private String password;
}