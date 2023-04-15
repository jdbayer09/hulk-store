package com.softcaribbean.hulkstore.api.repository;

import com.softcaribbean.hulkstore.api.models.entity.ProductEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IProductRepository extends CrudRepository<ProductEntity, Long> {
}
