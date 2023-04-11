package com.softcaribbean.hulkstore.api.service;

import com.softcaribbean.hulkstore.api.models.domain.User;

public interface IUserService {

    User findByEmail(String email);
}
