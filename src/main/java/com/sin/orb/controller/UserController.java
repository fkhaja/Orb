package com.sin.orb.controller;

import com.sin.orb.dto.UserDTO;
import com.sin.orb.security.CurrentUser;
import com.sin.orb.security.UserPrincipal;
import com.sin.orb.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    private UserService userService;
    private ModelMapper modelMapper;

    @Autowired
    public UserController(UserService userService, ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/user/me")
    @PreAuthorize("hasRole('USER')")
    public UserDTO getCurrentUser(@CurrentUser UserPrincipal userPrincipal) {
        return modelMapper.map(userService.findUserById(userPrincipal.getId()), UserDTO.class);
    }
}
