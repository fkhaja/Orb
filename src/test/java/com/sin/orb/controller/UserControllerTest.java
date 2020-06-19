package com.sin.orb.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser
    void getCurrentUserAuthenticatedRequestShouldSucceedWithStatusOk() throws Exception {
        mockMvc.perform(get("/user/me").contentType(MediaType.APPLICATION_JSON))
               .andExpect(status().isOk());
    }

    @Test
    void getCurrentUserNotAuthenticatedRequestShouldFailWithStatusUnauthorized() throws Exception {
        mockMvc.perform(get("/user/me").contentType(MediaType.APPLICATION_JSON))
               .andExpect(status().isUnauthorized());
    }
}