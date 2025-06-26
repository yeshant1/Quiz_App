package com.lpu.auth_service.model;

import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class UserPrincipal implements UserDetails {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private final Users user;

    public UserPrincipal(Users user) {
        this.user = user;
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public java.util.Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(() -> user.getRole());
    }

    @Override public boolean isAccountNonExpired() { return true; }
    @Override public boolean isAccountNonLocked() { return true; }
    @Override public boolean isCredentialsNonExpired() { return true; }
    @Override public boolean isEnabled() { return true; }

}