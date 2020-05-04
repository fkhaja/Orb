package com.sin.orb.service;

import com.sin.orb.domain.User;

import java.util.Optional;

public interface UserService {
    User findUserById(Long id);

    Boolean existsByEmail(String email);

    Optional<User> findByEmail(String email);

    User save(User user);
}
