package com.lpu.auth_service.service;

import com.lpu.auth_service.exception.CustomException;
import com.lpu.auth_service.model.Users;
import com.lpu.auth_service.repo.UserRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private JWTService jwtService;

    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private UserRepo userRepo;

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    public ResponseEntity<?> register(Users user) {
        if (userRepo.existsByUsername(user.getUsername())) { 
            throw new CustomException("User already exists. Please log in instead.", HttpStatus.FORBIDDEN);
        }

        if (userRepo.existsByEmail(user.getEmail())) { 
            throw new CustomException("Email already exists.", HttpStatus.FORBIDDEN);
        }

        if (userRepo.existsByMobile(user.getMobile())) { 
            throw new CustomException("Mobile number already exists.", HttpStatus.FORBIDDEN);
        }

        user.setRole("ADMIN");
        user.setPassword(encoder.encode(user.getPassword()));
        userRepo.save(user);

        String responseMessage = "Welcome " + user.getUsername() + "\nYour User ID is: " + user.getId();
        return ResponseEntity.status(HttpStatus.CREATED).body(responseMessage);
    }

    public ResponseEntity<?> verify(Users user) {
        Authentication auth = authManager.authenticate(
            new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));

        if (!auth.isAuthenticated()) {
            throw new CustomException("User not found.", HttpStatus.FORBIDDEN);
        }

        Users existingUser = userRepo.findByUsername(user.getUsername());

        String responseMessage = "Login Successful\n" + jwtService.generateToken(existingUser.getUsername(), existingUser.getRole());
        return ResponseEntity.status(HttpStatus.OK).body(responseMessage);
    }
}
