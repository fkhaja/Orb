package com.sin.orb.payload;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
public class LoginRequest {
    @Email
    @NotBlank
    @Size(min = 1, max = 129)
    @ApiModelProperty(required = true)
    private String email;

    @NotBlank
    @Size(min = 1, max = 128)
    @ApiModelProperty(required = true)
    private String password;
}
