package com.softcaribbean.hulkstore.api.repository;

import com.softcaribbean.hulkstore.api.models.entity.ProductStockEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IProductStockRepository extends CrudRepository<ProductStockEntity, Long> {
}
