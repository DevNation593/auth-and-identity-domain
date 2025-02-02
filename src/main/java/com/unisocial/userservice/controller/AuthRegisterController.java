package com.unisocial.userservice.controller;

import com.unisocial.userservice.dto.JwtResponse;
import com.unisocial.userservice.dto.UserRegistrationDto;
import com.unisocial.userservice.model.User;
import com.unisocial.userservice.security.JwtTokenUtil;
import com.unisocial.userservice.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*", maxAge = 3600)
public class AuthRegisterController {

    private final UserService userService;
    private final JwtTokenUtil jwtTokenUtil;

    public AuthRegisterController(UserService userService, JwtTokenUtil jwtTokenUtil) {
        this.userService = userService;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @PostMapping(
            value = "/register",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> register(@Valid @RequestBody UserRegistrationDto registrationDto) {
        try {
            User user = userService.registerUser(registrationDto);
            String token = jwtTokenUtil.generateToken(user);
            return ResponseEntity.ok(new JwtResponse(token));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage()));
        }
    }
}
