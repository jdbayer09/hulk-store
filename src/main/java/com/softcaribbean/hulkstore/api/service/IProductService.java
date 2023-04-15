package com.softcaribbean.hulkstore.api.service;

import com.softcaribbean.hulkstore.api.models.domain.Product;

import java.util.List;

public interface IProductService {

    List<Product> findAllProducts();
}
