package edu.modicon.app.infrastructure.security;

import edu.modicon.app.domain.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

/**
 * Application user credentials. Stored in SecurityApplication context
 */
public record AppUserDetails(
        String email,
        String password
) implements UserDetails {

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public static AppUserDetails fromUser(User user) {
        return new AppUserDetails(user.getEmail(), user.getPassword());
    }

    public static AppUserDetails ofUsername(String username) {
        return new AppUserDetails(username, null);
    }
}
