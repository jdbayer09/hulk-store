package com.softcaribbean.hulkstore.api.controllers;

import com.softcaribbean.hulkstore.api.exceptions.UserCreatedException;
import com.softcaribbean.hulkstore.api.models.mapper.user.UserRequestDomainMapper;
import com.softcaribbean.hulkstore.api.models.mapper.user.UserDomainResponseMapper;
import com.softcaribbean.hulkstore.api.models.request.user.CreateUserRequest;
import com.softcaribbean.hulkstore.api.models.response.user.CreateUserResponse;
import com.softcaribbean.hulkstore.api.service.IUserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/user")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserController {

    private final IUserService userService;
    private final UserRequestDomainMapper userRequestDomainMapper;
    private final UserDomainResponseMapper userDomainResponseMapper;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<CreateUserResponse> createUser(@RequestBody CreateUserRequest user) {
        var resp = userService.createUser(userRequestDomainMapper.userRequestToUser(user));
        return ResponseEntity.status(HttpStatus.CREATED).body(userDomainResponseMapper.userToResponse(resp));
    }
}
