package com.sin.orb.payload;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@RequiredArgsConstructor
public class LoginRequest {
    @Email
    @NonNull
    @NotBlank
    @Size(min = 1, max = 129)
    @ApiModelProperty(required = true)
    private final String email;

    @NonNull
    @NotBlank
    @Size(min = 1, max = 128)
    @ApiModelProperty(required = true)
    private final String password;
}