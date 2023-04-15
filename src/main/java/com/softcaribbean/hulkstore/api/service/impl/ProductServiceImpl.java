package com.softcaribbean.hulkstore.api.service.impl;

import com.softcaribbean.hulkstore.api.repository.IProductRepository;
import com.softcaribbean.hulkstore.api.repository.IProductStockRepository;
import com.softcaribbean.hulkstore.api.service.IProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements IProductService {

    private final IProductStockRepository productStockRepository;
    private final IProductRepository productRepository;

}
