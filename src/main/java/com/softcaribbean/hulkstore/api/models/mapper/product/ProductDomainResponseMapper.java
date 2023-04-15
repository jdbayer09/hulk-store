package com.softcaribbean.hulkstore.api.models.mapper.product;

import com.softcaribbean.hulkstore.api.models.domain.Product;
import com.softcaribbean.hulkstore.api.models.response.product.ProductResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductDomainResponseMapper {

    ProductResponse productToProductResponse(Product product);
}
