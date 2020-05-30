package com.sin.orb.dto;

import com.sin.orb.security.AuthProvider;
import lombok.Data;

@Data
public class UserDto {
    private Long id;
    private String username;
    private String email;
    private String imageUrl;
    private AuthProvider provider;
    private String providerId;
}
