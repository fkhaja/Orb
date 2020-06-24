package com.sin.orb.security;

import com.sin.orb.domain.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@SpringBootTest
class TokenProviderTest {

    @Autowired
    private TokenProvider tokenProvider;

    @Mock
    private Authentication auth;

    private static User userStub;

    @BeforeAll
    public static void setUp() {
        userStub = new User();
        userStub.setId(1L);
    }

    @BeforeEach
    void init() {
        when(auth.getPrincipal()).thenReturn(userStub);
    }

    @Test
    void validateTokenShouldReturnTrueWhenGetsValidToken() {
        String token = tokenProvider.createToken(auth);

        boolean isValid = tokenProvider.validateToken(token);

        assertThat(isValid).isTrue();
    }

    @Test
    void validateTokenShouldReturnFalseWhenGetsInvalidToken() {
        String invalidToken = "wrong";

        boolean isValid = tokenProvider.validateToken(invalidToken);

        assertThat(isValid).isFalse();
    }

    @Test
    void createTokenShouldReturnValidToken() {
        String token = tokenProvider.createToken(auth);

        assertThat(token).isNotBlank();
        assertThat(tokenProvider.validateToken(token)).isTrue();
    }

    @Test
    void getUserIdFromTokenShouldReturnCorrectId() {
        String token = tokenProvider.createToken(auth);

        Long id = tokenProvider.getUserIdFromToken(token);

        assertThat(id).isNotNull();
        assertThat(id).isEqualTo(userStub.getId());
    }
}