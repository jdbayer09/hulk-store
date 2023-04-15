package com.softcaribbean.hulkstore.api.service.impl;

import com.softcaribbean.hulkstore.api.exceptions.NotProductException;
import com.softcaribbean.hulkstore.api.models.domain.Product;
import com.softcaribbean.hulkstore.api.models.mapper.product.ProductEntityDomainMapper;
import com.softcaribbean.hulkstore.api.repository.IProductRepository;
import com.softcaribbean.hulkstore.api.repository.IProductStockRepository;
import com.softcaribbean.hulkstore.api.service.IProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements IProductService {

    private final IProductStockRepository productStockRepository;
    private final IProductRepository productRepository;
    private final ProductEntityDomainMapper productEntityDomainMapper;

    @Override
    public List<Product> findAllProducts() {
        var listProducts = productRepository.findAll();

        if (!listProducts.iterator().hasNext()) {
            throw new NotProductException(listProducts, "No existe ningún producto.");
        }

        return StreamSupport
                .stream(listProducts.spliterator(), false)
                .map(productEntityDomainMapper::productEntityToProduct)
                .toList();
    }
}
