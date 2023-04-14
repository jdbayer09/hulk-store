package com.softcaribbean.hulkstore.api.models.mapper.user;

import com.softcaribbean.hulkstore.api.models.domain.User;
import com.softcaribbean.hulkstore.api.models.request.user.CreateUserRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserRequestDomainMapper {
    User userRequestToUser(CreateUserRequest createUserRequest);
}
