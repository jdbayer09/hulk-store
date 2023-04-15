package com.softcaribbean.hulkstore.api.controllers;

import com.softcaribbean.hulkstore.api.models.mapper.product.ProductDomainResponseMapper;
import com.softcaribbean.hulkstore.api.models.response.product.ProductResponse;
import com.softcaribbean.hulkstore.api.service.IProductService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/v1/product")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductController {

    private final IProductService productService;
    private final ProductDomainResponseMapper productDomainResponseMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> createProducto() {
        return null;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<ProductResponse>> listAllProducts() {
        var listProducts = productService.findAllProducts();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(
                        listProducts
                                .stream()
                                .map(productDomainResponseMapper::productToProductResponse)
                                .toList()
                );
    }

    @PutMapping("/add_stock/{idProduct}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> addStock(@PathVariable Long idProduct) {
        return null;
    }

    @PutMapping("/remove_stock/{idProduct}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> removeStock(@PathVariable Long idProduct) {
        return null;
    }
}
