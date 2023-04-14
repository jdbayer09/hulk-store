package com.softcaribbean.hulkstore.api.service.impl;

import com.softcaribbean.hulkstore.api.exceptions.UserCreatedException;
import com.softcaribbean.hulkstore.api.models.domain.User;
import com.softcaribbean.hulkstore.api.models.entity.UserEntity;
import com.softcaribbean.hulkstore.api.models.mapper.user.UserEntityDomainMapper;
import com.softcaribbean.hulkstore.api.repository.IUserRepository;
import com.softcaribbean.hulkstore.api.service.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements IUserService {

    private final IUserRepository userRepository;
    private final UserEntityDomainMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User findByEmail(String email) {
        UserEntity entity = userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("No existe el usuario"));
        return userMapper.userEntityToUser(entity);
    }

    @Override
    @Transactional
    public User createUser(User user) {
        var newUser = new UserEntity();

        newUser.setEmail(user.getEmail());
        newUser.setCreatedAt(ZonedDateTime.now());
        newUser.setName(user.getName());
        newUser.setActive(true);
        newUser.setLastName(user.getLastName());
        newUser.setPass(passwordEncoder.encode(user.getPassword()));

        try {
            var resp = userRepository.save(newUser);
            return userMapper.userEntityToUser(resp);
        } catch (Exception ex) {
            throw new UserCreatedException("El Usuario con el email: [" + user.getEmail() + "] ya se encuentra registrado", ex);
        }
    }
}