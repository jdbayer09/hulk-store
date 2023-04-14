package com.softcaribbean.hulkstore.api.models.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.List;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class User implements Serializable, UserDetails {


    private Long id;

    private ZonedDateTime createdAt;

    @NonNull
    private String email;

    @NonNull
    private String pass;

    @NonNull
    private String name;

    @NonNull
    private String lastName;

    private boolean active;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getPassword() {
        return this.getPass();
    }

    @Override
    public String getUsername() {
        return this.getEmail();
    }

    @Override
    public boolean isEnabled() {
        return this.isActive();
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
}
