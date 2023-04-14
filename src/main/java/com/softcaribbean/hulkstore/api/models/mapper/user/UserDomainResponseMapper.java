package com.softcaribbean.hulkstore.api.models.mapper.user;

import com.softcaribbean.hulkstore.api.models.domain.User;
import com.softcaribbean.hulkstore.api.models.response.user.CreateUserResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserDomainResponseMapper {
    CreateUserResponse userToResponse(User user);
}
