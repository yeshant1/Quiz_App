package com.lpu.auth_service.model;
 
import org.hibernate.validator.constraints.UniqueElements;
 
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
 
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor 
@Table(name = "users", uniqueConstraints = {
	    @UniqueConstraint(columnNames = {"email"}),
	    @UniqueConstraint(columnNames = {"mobile"})
	})
public class Users {
 
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
 
    @NotBlank(message = "Username is required")
    @Size(min = 4, message = "Username should be atleast 4 character long")
    @Column(unique = true, nullable = false)
    private String username;
 
    @NotBlank(message = "Password is required")
    @Size(min = 6, message = "Password must be at least 6 characters long")
    @Column(nullable = false)
    private String password;
 
//    @Pattern(regexp = "^(USER)$", message = "Role must be USER")
    private String role;
    @NotBlank(message = "Email is required")
    @Pattern(regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$", message = "Invalid email format")  
    @Column(unique = true, nullable = false)
    private String email;
    @NotBlank(message = "Mobile number is required")
    @Pattern(regexp = "^[6-9]\\d{9}$", message = "Invalid mobile number")
//    @UniqueElements
    @Column(unique = true, nullable = false)
    private String mobile;
 
    @Override
    public String toString() {
        return "Users [id=" + id + ", username=" + username + ", role=" + role + "]";
    }
}