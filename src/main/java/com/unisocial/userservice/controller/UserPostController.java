package com.unisocial.userservice.controller;

import com.unisocial.userservice.dto.UserRegistrationDto;
import com.unisocial.userservice.model.User;
import com.unisocial.userservice.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@CrossOrigin
public class UserPostController {

    private final UserService userService;

    public UserPostController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody UserRegistrationDto registrationDto) {
        return ResponseEntity.ok(userService.registerUser(registrationDto));
    }
}