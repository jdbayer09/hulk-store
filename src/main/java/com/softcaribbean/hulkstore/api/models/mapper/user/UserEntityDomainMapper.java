package com.softcaribbean.hulkstore.api.models.mapper.user;

import com.softcaribbean.hulkstore.api.models.domain.User;
import com.softcaribbean.hulkstore.api.models.entity.UserEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserEntityDomainMapper {
    User userEntityToUser(UserEntity userEntity);
}
