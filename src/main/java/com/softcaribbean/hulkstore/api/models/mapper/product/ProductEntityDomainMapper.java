package com.softcaribbean.hulkstore.api.models.mapper.product;

import com.softcaribbean.hulkstore.api.models.domain.Product;
import com.softcaribbean.hulkstore.api.models.entity.ProductEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductEntityDomainMapper {

    Product productEntityToProduct(ProductEntity product);
}
