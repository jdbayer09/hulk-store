package com.softcaribbean.hulkstore.api.service;

import com.softcaribbean.hulkstore.api.models.domain.Product;
import com.softcaribbean.hulkstore.api.models.domain.User;
import com.softcaribbean.hulkstore.api.models.request.product.AddStockProduct;
import com.softcaribbean.hulkstore.api.models.request.product.CreateProductRequest;
import com.softcaribbean.hulkstore.api.models.request.product.RemoveStockProduct;

import java.util.List;

public interface IProductService {

    List<Product> findAllProducts();
    Product createProduct(CreateProductRequest product, User user);
    Product addProductStock(AddStockProduct addStockProduct, User user, Long productId);
    Product removeProductStock(RemoveStockProduct removeStockProduct, User user, Long productId);
}
