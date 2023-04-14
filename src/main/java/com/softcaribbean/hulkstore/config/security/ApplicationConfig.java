package com.softcaribbean.hulkstore.config.security;

import com.softcaribbean.hulkstore.api.service.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;

@Configuration
@RequiredArgsConstructor
public class ApplicationConfig {

    private final IUserService userService;
    @Bean
    public UserDetailsService userDetailsService() {
        return userService::findByEmail;
    }

}