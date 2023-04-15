package com.softcaribbean.hulkstore.api.models.mapper.user;

import com.softcaribbean.hulkstore.api.models.domain.User;
import com.softcaribbean.hulkstore.api.models.response.user.CreateUserResponse;
import org.mapstruct.Mapper;

import java.time.ZonedDateTime;

@Mapper(componentModel = "spring")
public interface UserDomainResponseMapper {
    default CreateUserResponse userToResponse(User user) {
        if ( user == null ) {
            return null;
        }

        Long id = user.getId();
        String name = user.getName();
        String lastName = user.getLastName();
        String email = user.getEmail();
        ZonedDateTime createdAt = user.getCreatedAt();

        return new CreateUserResponse( id, name, lastName, email, createdAt );
    }
}
