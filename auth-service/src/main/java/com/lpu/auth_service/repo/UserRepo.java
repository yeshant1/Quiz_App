package com.lpu.auth_service.repo;
 
import java.util.Optional;
 
import org.springframework.data.jpa.repository.JpaRepository;
 
import com.lpu.auth_service.model.Users;
 
public interface UserRepo extends JpaRepository<Users, Integer> {
	Users findByUsername(String username);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
	boolean existsByMobile(String mobile);
}