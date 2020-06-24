package com.sin.orb.service;

import com.sin.orb.domain.User;
import com.sin.orb.payload.SignUpRequest;

public interface UserService {

    Boolean existsByEmail(String email);

    User save(User user);

    void registerUser(SignUpRequest signUpRequest);
}