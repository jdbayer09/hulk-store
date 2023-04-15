package com.softcaribbean.hulkstore.api.models.response.product;

import com.softcaribbean.hulkstore.api.models.enums.ProductType;

import java.time.ZonedDateTime;
import java.util.List;

public record ProductResponse(
        Long id,
        ProductType type,
        String name,
        Double price,
        int totalPieces,
        ZonedDateTime createdAt,
        List<ProductStockResponse> transactions
) {
}
