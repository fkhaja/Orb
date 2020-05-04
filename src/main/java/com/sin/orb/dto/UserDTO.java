package com.sin.orb.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.sin.orb.domain.AuthProvider;
import lombok.Data;

import java.util.List;

@Data
public class UserDTO {
    private Long id;
    private String username;
    private String email;
    private String imageUrl;
    private Boolean emailVerified = false;
    @JsonIgnore
    private String password;
    private AuthProvider provider;
    private String providerId;
    @JsonManagedReference
    @JsonIgnore
    private List<TaskCardDTO> taskCards;
}
