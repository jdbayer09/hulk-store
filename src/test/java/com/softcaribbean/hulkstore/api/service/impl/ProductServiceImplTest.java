package com.softcaribbean.hulkstore.api.service.impl;

import com.softcaribbean.hulkstore.api.models.domain.Product;
import com.softcaribbean.hulkstore.api.models.entity.ProductEntity;
import com.softcaribbean.hulkstore.api.models.mapper.product.ProductEntityDomainMapper;
import com.softcaribbean.hulkstore.api.repository.IProductRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {
    @InjectMocks
    private ProductServiceImpl productService;
    @Mock
    private IProductRepository productRepository;
    @Mock
    private ProductEntityDomainMapper productEntityDomainMapper;

    @Test
    void findAllProducts() {
        List<ProductEntity> productEntities = new ArrayList<>();
        var product1 = new ProductEntity();
        product1.setId(1L);
        product1.setName("Product 1");
        productEntities.add(product1);

        var product2 = new ProductEntity();
        product1.setId(2L);
        product1.setName("Product 2");
        productEntities.add(product2);

        when(productRepository.findAll()).thenReturn(productEntities);

        List<Product> expectedProducts = new ArrayList<>();
        var expectedProduct1 = new Product();
        expectedProduct1.setId(1L);
        expectedProduct1.setName("Product 1");
        expectedProducts.add(expectedProduct1);

        var expectedProduct2 = new Product();
        expectedProduct2.setId(2L);
        expectedProduct2.setName("Product 2");
        expectedProducts.add(expectedProduct2);

        when(productEntityDomainMapper.productEntityToProduct(productEntities.get(0))).thenReturn(expectedProducts.get(0));
        when(productEntityDomainMapper.productEntityToProduct(productEntities.get(1))).thenReturn(expectedProducts.get(1));

        List<Product> actualProducts = productService.findAllProducts();
        Assertions.assertEquals(expectedProducts, actualProducts);
    }
}