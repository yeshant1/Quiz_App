package com.lpu.auth_service.service;
 
import com.lpu.auth_service.model.Users;
import com.lpu.auth_service.model.UserPrincipal;
import com.lpu.auth_service.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;
 
@Service
public class MyUserDetailsService implements UserDetailsService {
 
    @Autowired
    private UserRepo repo;
 
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users user = repo.findByUsername(username);
        if (user == null) throw new UsernameNotFoundException("User not found");
        return new UserPrincipal(user);
    }
}