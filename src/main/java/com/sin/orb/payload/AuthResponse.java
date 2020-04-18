package com.sin.orb.payload;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class AuthResponse {
    @NonNull
    private String accessToken;

    private String tokenType = "Bearer";
}