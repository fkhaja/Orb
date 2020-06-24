package com.sin.orb.service;

import com.sin.orb.domain.User;
import com.sin.orb.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class UserServiceTest {

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Test
    void existsByEmail() {
        when(userRepository.existsByEmail(anyString())).thenReturn(anyBoolean());
        userService.existsByEmail("test@mail.com");

        verify(userRepository).existsByEmail(anyString());
    }

    @Test
    void save() {
        when(userRepository.save(any(User.class))).thenReturn(any(User.class));
        userService.save(new User());

        verify(userRepository).save(any(User.class));
    }
}