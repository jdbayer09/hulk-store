package com.softcaribbean.hulkstore.api.service.impl;

import com.softcaribbean.hulkstore.api.exceptions.UserCreatedException;
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
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyString;
import static org.junit.jupiter.api.Assertions.fail;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @InjectMocks
    private UserServiceImpl userService;
    @Mock
    private IUserRepository userRepository;
    @Mock
    private UserEntityDomainMapper userMapper;
    @Mock
    private PasswordEncoder passwordEncoder;

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

        Assertions.assertThrows(UsernameNotFoundException.class, () -> userService.findByEmail(email));

        try {
            var resp = userService.findByEmail(email);
            fail("Expected exception not thrown");
        } catch (Exception ex) {
            Assertions.assertTrue(ex instanceof UsernameNotFoundException);
            Assertions.assertEquals(errorMessage, ex.getMessage());
        }
    }

    @Test
    void when_createUser() {
        var user = new User();
        user.setEmail("jdbayer@softcaribbean.com.com");
        user.setName("Julian");
        user.setPass("12345");
        user.setLastName("Bayer");

        var userEntity = new UserEntity();
        userEntity.setEmail(user.getEmail());
        userEntity.setName(user.getName());
        userEntity.setActive(true);
        userEntity.setLastName(user.getLastName());
        userEntity.setPass("hashedPassword");

        when(userRepository.save(any(UserEntity.class))).thenReturn(userEntity);
        when(passwordEncoder.encode(anyString())).thenReturn("hashedPassword");

        var validUser = new User();
        validUser.setEmail(user.getEmail());
        validUser.setName(user.getName());
        validUser.setActive(true);
        validUser.setLastName(user.getLastName());
        validUser.setPass("hashedPassword");

        when(userMapper.userEntityToUser(any(UserEntity.class))).thenReturn(validUser);

        var createdUser = userService.createUser(user);

        Assertions.assertEquals(userEntity.getEmail(), createdUser.getEmail());
        Assertions.assertEquals(userEntity.getName(), createdUser.getName());
        Assertions.assertEquals(userEntity.getLastName(), createdUser.getLastName());
        Assertions.assertEquals(userEntity.getPass(), createdUser.getPass());
    }
    @Test
    void when_createUser_is_created() {
        var user = new User();
        user.setEmail("jdbayer@softcaribbean.com.com");
        user.setName("Julian");
        user.setPass("12345");
        user.setLastName("Bayer");

        var errorMessage = "El Usuario con el email: [" + user.getEmail() + "] ya se encuentra registrado";

        when(passwordEncoder.encode(anyString())).thenReturn("hashedPassword");
        when(userRepository.save(any(UserEntity.class))).thenThrow(new UserCreatedException(""));

        Assertions.assertThrows(UserCreatedException.class, () -> userService.createUser(user));
        try {
            var createdUser = userService.createUser(user);
            fail("Expected exception not thrown");
        } catch (Exception ex) {
            Assertions.assertEquals(errorMessage, ex.getMessage());
        }
    }
}