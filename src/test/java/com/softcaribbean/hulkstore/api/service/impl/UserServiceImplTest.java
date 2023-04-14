package com.softcaribbean.hulkstore.api.service.impl;

import com.softcaribbean.hulkstore.api.models.domain.User;
import com.softcaribbean.hulkstore.api.models.entity.UserEntity;
import com.softcaribbean.hulkstore.api.models.mapper.user.UserEntityDomainMapper;
import com.softcaribbean.hulkstore.api.repository.IUserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.fail;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @InjectMocks
    private UserServiceImpl userService;
    @Mock
    private IUserRepository userRepository;
    @Mock
    private UserEntityDomainMapper userMapper;

    @Test
    void when_findByEmail_is_not_null() {
        var email = "jdbayer@softcaribbean.com";
        var userEntity = new UserEntity();
        userEntity.setEmail(email);
        var user = new User();
        user.setEmail(email);
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(userEntity));
        when(userMapper.userEntityToUser(userEntity)).thenReturn(user);
        var resp = userService.findByEmail(email);
        Assertions.assertNotNull(resp);
        Assertions.assertEquals(user, resp);
    }

    @Test
    void when_findByEmail_is_null() {
        var email = "jdbayer@softcaribbean.com";
        var errorMessage = "No existe el usuario";
        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());
        try {
            var resp = userService.findByEmail(email);
            fail("Expected exception not thrown");
        } catch (Exception ex) {
            Assertions.assertTrue(ex instanceof UsernameNotFoundException);
            Assertions.assertEquals(errorMessage, ex.getMessage());
        }
    }
}