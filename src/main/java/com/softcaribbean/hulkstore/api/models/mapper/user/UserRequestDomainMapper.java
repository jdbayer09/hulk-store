package com.softcaribbean.hulkstore.api.models.mapper.user;

import com.softcaribbean.hulkstore.api.models.domain.User;
import com.softcaribbean.hulkstore.api.models.request.user.CreateUserRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserRequestDomainMapper {

    default User userRequestToUser(CreateUserRequest createUserRequest) {
        if ( createUserRequest == null ) {
            return null;
        }

        User.UserBuilder user = User.builder();

        user.email( createUserRequest.email() );
        user.pass( createUserRequest.pass() );
        user.name( createUserRequest.name() );
        user.lastName( createUserRequest.lastName() );

        return user.build();
    }
}
