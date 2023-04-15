package com.softcaribbean.hulkstore.api.models.response.product;

import com.softcaribbean.hulkstore.api.models.response.user.UserResponse;

import java.time.ZonedDateTime;

public record ProductStockResponse(
        Long id,
        UserResponse user,
        ZonedDateTime createdAt,
        int quantity
) {
}
