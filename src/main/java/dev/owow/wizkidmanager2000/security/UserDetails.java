package dev.owow.wizkidmanager2000.security;

import dev.owow.wizkidmanager2000.entity.AccountEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collections;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDetails implements org.springframework.security.core.userdetails.UserDetails {
    private String username;
    private String password;
    private List<SimpleGrantedAuthority> authorities;

    public UserDetails(AccountEntity accountEntity) {
        this.username = accountEntity.getEmail();
        this.password = accountEntity.getPassword();
        this.authorities = Collections.emptyList();
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
}
