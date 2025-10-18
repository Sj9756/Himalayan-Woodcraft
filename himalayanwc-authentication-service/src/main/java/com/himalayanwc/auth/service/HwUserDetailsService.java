package com.himalayanwc.auth.service;

import com.himalayanwc.auth.model.HwUserDetails;
import com.himalayanwc.auth.model.User;
import com.himalayanwc.auth.repo.UserRepo;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class HwUserDetailsService implements UserDetailsService {
    private final UserRepo userRepo;

    public HwUserDetailsService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String email){
       Optional<User> user= userRepo.findByEmail(email);
       if (user.isPresent()){
           return new HwUserDetails(user.get());
       }
     throw new UsernameNotFoundException("User Not Found");
    }
}
