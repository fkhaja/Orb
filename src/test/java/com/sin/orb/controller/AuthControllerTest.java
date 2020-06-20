package com.sin.orb.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sin.orb.payload.LoginRequest;
import com.sin.orb.payload.SignUpRequest;
import com.sin.orb.security.TokenProvider;
import com.sin.orb.service.UserService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private AuthenticationManager authManager;

    @MockBean
    private TokenProvider tokenProvider;

    @Autowired
    private ObjectMapper mapper;

    private static SignUpRequest signUpRequestStub;

    private static LoginRequest loginRequestStub;

    @BeforeAll
    public static void setUp() {
        signUpRequestStub = new SignUpRequest("username", "test@mail.com", "password");
        loginRequestStub = new LoginRequest("test@mail.com", "password");
    }

    @Test
    void registerUserShouldReturnStatusCreated() throws Exception {
        when(userService.existsByEmail(anyString())).thenReturn(false);

        mockMvc.perform(post("/auth/signup")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(mapper.writeValueAsString(signUpRequestStub)))
               .andExpect(status().isCreated());
    }

    @Test
    void whenRegisterUserGetsExistingEmailThanReturnStatusConflict() throws Exception {
        when(userService.existsByEmail(anyString())).thenReturn(true);

        mockMvc.perform(post("/auth/signup")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(mapper.writeValueAsString(signUpRequestStub)))
               .andExpect(status().isConflict());
    }

    @Test
    void registerUserCorrectlySaveUser() throws Exception {
        when(userService.existsByEmail(anyString())).thenReturn(false);
        doNothing().when(userService).registerUser(any(SignUpRequest.class));

        mockMvc.perform(post("/auth/signup")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(mapper.writeValueAsString(signUpRequestStub)))
               .andExpect(status().isCreated())
               .andExpect(jsonPath("$.success", equalTo(true)));

        verify(userService).registerUser(any(SignUpRequest.class));
    }

    @Test
    void authenticateUserShouldReturnStatusOk() throws Exception {
        when(authManager.authenticate(any(Authentication.class))).thenReturn(mock(Authentication.class));
        when(tokenProvider.createToken(any(Authentication.class))).thenReturn(anyString());

        mockMvc.perform(post("/auth/login")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(mapper.writeValueAsString(loginRequestStub)))
               .andExpect(status().isOk());
    }

    @Test
    void authenticateUserShouldAuthenticateAndReturnAccessToken() throws Exception {
        when(authManager.authenticate(any(Authentication.class))).thenReturn(mock(Authentication.class));
        when(tokenProvider.createToken(any(Authentication.class))).thenReturn(anyString());

        mockMvc.perform(post("/auth/login")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(mapper.writeValueAsString(loginRequestStub)))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.accessToken", notNullValue()))
               .andExpect(jsonPath("$.tokenType", equalTo("Bearer")));

        verify(authManager).authenticate(any(Authentication.class));
        verify(tokenProvider).createToken(any(Authentication.class));
    }
}