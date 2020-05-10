package com.sin.orb.service;

import com.sin.orb.domain.User;

public interface UserService {

    Boolean existsByEmail(String email);

    User save(User user);
}
