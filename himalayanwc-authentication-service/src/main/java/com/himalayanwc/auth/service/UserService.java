package com.himalayanwc.auth.service;

import com.himalayanwc.auth.exception.ResourceAlreadyExistsException;
import com.himalayanwc.auth.exception.ResourceNotFoundException;
import com.himalayanwc.auth.model.User;
import com.himalayanwc.auth.model.UserDto;
import com.himalayanwc.auth.repo.UserRepo;

import com.himalayanwc.auth.utils.JwtUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
   private final UserRepo userRepo;
    private final PasswordEncoder encoder;
    private final AuthenticationManager authenticationManager;

    public UserService(UserRepo userRepo, PasswordEncoder encoder, AuthenticationManager authenticationManager) {
        this.userRepo = userRepo;
        this.encoder = encoder;
        this.authenticationManager = authenticationManager;
    }

    public User findById(int id) throws ResourceNotFoundException {
        return userRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("User Not Found"));
    }

    public UserDto register(User user) {
        if (user == null){
            throw new IllegalArgumentException("User cannot be null");
        }
        if (userRepo.existsByEmail(user.getEmail())) {
            throw new ResourceAlreadyExistsException("User with email " + user.getEmail() + " already exists");
        }
        user.setPassword(encoder.encode(user.getPassword()));
        User savedUser= userRepo.save(user);
        return new UserDto(savedUser);
    }
    public UserDto update(User user){
        User savedUser=findById(user.getId());
        if (savedUser!=null){
            user.setPassword(encoder.encode(user.getPassword()));
            User updatedUser=userRepo.save(user);
            return new UserDto(updatedUser);
        }
        throw new ResourceNotFoundException("User Not exist");
    }

    public String  login(String email, String password) {
        if (userRepo.existsByEmail(email)) {
            try {
                Authentication authentication = authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(email, password));

                if (authentication.isAuthenticated()) {
                    return new JwtUtil().generateJwtToken(email);
                }
            } catch (BadCredentialsException e) {
                throw new ResourceNotFoundException("Invalid email or password");
            } catch (AuthenticationException e) {
                throw new ResourceNotFoundException("Authentication failed: " + e.getMessage());
            }
        }
        throw new ResourceNotFoundException("User Not exist");
}

    public boolean delete(int id) {
        if (userRepo.existsById(id)) {
            userRepo.deleteById(id);
            return true;
        }
        return false;
    }
}