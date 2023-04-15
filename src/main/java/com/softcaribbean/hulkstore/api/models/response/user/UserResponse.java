package com.softcaribbean.hulkstore.api.models.response.user;


import java.time.ZonedDateTime;

public record UserResponse(
        Long id,
        ZonedDateTime createdAt,
        String email,
        String name,
        String lastName
) {
}
