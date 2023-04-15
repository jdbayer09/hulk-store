package com.softcaribbean.hulkstore.api.service;

import com.softcaribbean.hulkstore.api.models.domain.Product;
import com.softcaribbean.hulkstore.api.models.domain.User;
import com.softcaribbean.hulkstore.api.models.request.product.CreateProductRequest;

import java.util.List;

public interface IProductService {

    List<Product> findAllProducts();

    Product createProduct(CreateProductRequest product, User user);
}
