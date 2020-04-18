package com.sin.orb.payload;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class ApiResponse {
    @NonNull
    private boolean success;

    @NonNull
    private String message;
}
