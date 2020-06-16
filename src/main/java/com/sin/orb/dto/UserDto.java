package com.sin.orb.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Null;
import javax.validation.constraints.Size;

@Data
public class UserDto {
    @Null
    @ApiModelProperty(readOnly = true)
    private Long id;

    @NotBlank
    @Size(min = 4, max = 16)
    @ApiModelProperty(required = true)
    private String username;

    @Email
    @NotBlank
    @Size(min = 1, max = 129)
    @ApiModelProperty(required = true)
    private String email;

    @Size(max = 2048)
    private String imageUrl;
}
