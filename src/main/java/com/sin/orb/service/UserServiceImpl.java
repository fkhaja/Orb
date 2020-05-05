package com.sin.orb.service;

import com.sin.orb.domain.User;
import com.sin.orb.exceptions.ResourceNotFoundException;
import com.sin.orb.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private UserRepository repository;

    @Autowired
    public UserServiceImpl(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public User findUserById(Long id) {
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
    }

    @Override
    public Boolean existsByEmail(String email){
        return repository.existsByEmail(email);
    }

    @Override
    public Optional<User> findByEmail(String email){
        return repository.findByEmail(email);
    }

    @Override
    public User save(User user){
        return repository.save(user);
    }
}
