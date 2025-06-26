package com.lpu.auth_service.controller;
 
import com.lpu.auth_service.model.Users;
import com.lpu.auth_service.service.UserService;
import jakarta.validation.Valid;
 
import java.util.List;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
 
@RestController
@RequestMapping("auth")
public class AuthController {
 
    @Autowired
    private UserService service;
    @PostMapping("register")
    public ResponseEntity<?> register(@Valid @RequestBody Users user, BindingResult result) {
        if (result.hasErrors()) {
            // Extract only the default messages
            List<String> errors = result.getFieldErrors()
                                        .stream()
                                        .map(error -> error.getDefaultMessage())
                                        .toList();
            return ResponseEntity.badRequest().body(errors);
        }
        ResponseEntity<?> response = service.register(user);
        return ResponseEntity.status(response.getStatusCode()).body(response.getBody());
    }
 
    @PostMapping("login")
    public ResponseEntity<?> login(@RequestBody Users user) {
    	ResponseEntity<?> response = service.verify(user);
        return ResponseEntity.status(response.getStatusCode()).body(response.getBody());
    }
 
 
}


//
//    // 🔐 Admin-only endpoint
//    @PreAuthorize("hasRole('ADMIN')")
//    @GetMapping("admin/data")
//    public ResponseEntity<String> getAdminData() {
//        return ResponseEntity.ok("This is protected admin data.");
//    }
//
//    // 👤 User-only endpoint
//    @PreAuthorize("hasRole('USER')")
//    @GetMapping("user/data")
//    public ResponseEntity<String> getUserData() {
//        return ResponseEntity.ok("This is protected user data.");
//    }
