package com.softcaribbean.hulkstore.api.models.mapper;

import com.softcaribbean.hulkstore.api.models.domain.User;
import com.softcaribbean.hulkstore.api.models.entity.UserEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserEntityMapper {
    User userEntityToUser(UserEntity userEntity);
}
