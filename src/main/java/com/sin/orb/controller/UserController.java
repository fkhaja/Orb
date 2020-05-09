package com.sin.orb.controller;

import com.sin.orb.domain.User;
import com.sin.orb.dto.UserDTO;
import com.sin.orb.security.CurrentUser;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    private ModelMapper modelMapper;

    @Autowired
    public UserController(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @GetMapping("/user/me")
    @PreAuthorize("hasRole('USER')")
    public UserDTO getCurrentUser(@CurrentUser User user) {
        return modelMapper.map(user, UserDTO.class);
    }
}
