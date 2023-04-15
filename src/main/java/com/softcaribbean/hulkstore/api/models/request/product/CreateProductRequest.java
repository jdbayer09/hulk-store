package com.softcaribbean.hulkstore.api.models.request.product;

import com.softcaribbean.hulkstore.api.models.enums.ProductType;

public record CreateProductRequest(
        String name,
        Double price,
        ProductType type,
        int quantityPieces
) {
}
