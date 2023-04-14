package com.softcaribbean.hulkstore.api.models.request.user;

import lombok.NonNull;

public record CreateUserRequest(
    @NonNull String email,
    @NonNull String pass,
    @NonNull String name,
    @NonNull String lastName) {
}
