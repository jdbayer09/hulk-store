package com.softcaribbean.hulkstore.api.models.response.user;

import java.time.ZonedDateTime;

public record CreateUserResponse(
        Long id,
        String name,
        String lastName,
        String email,
        ZonedDateTime createdAt
) {
}
