package com.himalayanwc.auth.controller;

import com.himalayanwc.auth.model.User;
import com.himalayanwc.auth.model.UserDto;
import com.himalayanwc.auth.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<UserDto> registerUser(@RequestBody User user) {
               return new ResponseEntity<>(userService.register(user), HttpStatus.CREATED);
    }
    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestParam String email, @RequestParam String password) {
        return new ResponseEntity<>(userService.login(email,password),HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<UserDto>updateUserDetails(@RequestBody User user){
        return new ResponseEntity<>(userService.update(user),HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Boolean>deleteUserDetails(@PathVariable int id){
        return new ResponseEntity<>(userService.delete(id),HttpStatus.OK);
    }
    }
