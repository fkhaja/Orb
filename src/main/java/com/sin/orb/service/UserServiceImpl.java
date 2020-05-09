package com.sin.orb.service;

import com.sin.orb.domain.User;
import com.sin.orb.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private UserRepository repository;

    @Autowired
    public UserServiceImpl(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public Boolean existsByEmail(String email){
        return repository.existsByEmail(email);
    }

    @Override
    public User save(User user){
        return repository.save(user);
    }
}
