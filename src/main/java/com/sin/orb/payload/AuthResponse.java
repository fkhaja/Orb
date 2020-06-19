package com.sin.orb.payload;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class AuthResponse {
    @NonNull
    private final String accessToken;

    private final String tokenType = "Bearer";
}