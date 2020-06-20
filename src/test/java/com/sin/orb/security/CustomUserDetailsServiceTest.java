package com.sin.orb.security;

import com.sin.orb.domain.User;
import com.sin.orb.exception.ResourceNotFoundException;
import com.sin.orb.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class CustomUserDetailsServiceTest {

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private CustomUserDetailsService userService;

    @Test
    void loadUserByUsernameShouldReturnCorrectUserDetails() {
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(new User()));

        UserDetails userDetails = userService.loadUserByUsername("username");

        assertThat(userDetails.getAuthorities().contains(Role.USER));
        verify(userRepository).findByEmail(anyString());
    }

    @Test
    void whenLoadUserByUsernameGetsNonexistentEmailThanThrowException() {
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () -> userService.loadUserByUsername(anyString()));
        verify(userRepository).findByEmail(anyString());
    }

    @Test
    void loadUserByIdShouldReturnCorrectUserDetails() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(new User()));

        UserDetails userDetails = userService.loadUserById(1L);

        assertThat(userDetails.getAuthorities().contains(Role.USER));
        verify(userRepository).findById(anyLong());
    }

    @Test
    void whenLoadUserByIdGetsNonexistentIdThanThrowException() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> userService.loadUserById(anyLong()));
        verify(userRepository).findById(anyLong());
    }
}