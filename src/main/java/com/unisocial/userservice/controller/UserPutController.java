package com.unisocial.userservice.controller;

import com.unisocial.userservice.dto.UserUpdateDto;
import com.unisocial.userservice.model.User;
import com.unisocial.userservice.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@CrossOrigin
public class UserPutController {

    private final UserService userService;

    public UserPutController(UserService userService) {
        this.userService = userService;
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody UserUpdateDto userUpdateDto) {
        return ResponseEntity.ok(userService.updateUser(id, userUpdateDto));
    }
}