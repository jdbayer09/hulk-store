package com.softcaribbean.hulkstore.api.service.impl;

import com.softcaribbean.hulkstore.api.models.domain.User;
import com.softcaribbean.hulkstore.api.models.entity.UserEntity;
import com.softcaribbean.hulkstore.api.models.mapper.UserEntityMapper;
import com.softcaribbean.hulkstore.api.repository.IUserRepository;
import com.softcaribbean.hulkstore.api.service.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements IUserService {

    private final IUserRepository userRepository;
    private final UserEntityMapper userMapper;

    @Override
    public User findByEmail(String email) {
        UserEntity entity = userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("No existe el usuario"));
        return userMapper.userEntityToUser(entity);
    }
}
