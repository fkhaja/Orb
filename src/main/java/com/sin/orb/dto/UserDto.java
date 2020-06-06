package com.sin.orb.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Null;
import javax.validation.constraints.Size;

@Data
public class UserDto {
    @Null
    private Long id;

    @NotBlank
    @Size(min = 4, max = 16)
    private String username;

    @Email
    @NotBlank
    @Size(min = 1, max = 129)
    private String email;

    @Size(max = 2048)
    private String imageUrl;
}
