package com.softcaribbean.hulkstore.api.controllers;

import com.softcaribbean.hulkstore.api.models.domain.User;
import com.softcaribbean.hulkstore.api.models.mapper.product.ProductDomainResponseMapper;
import com.softcaribbean.hulkstore.api.models.request.product.AddStockProduct;
import com.softcaribbean.hulkstore.api.models.request.product.CreateProductRequest;
import com.softcaribbean.hulkstore.api.models.request.product.RemoveStockProduct;
import com.softcaribbean.hulkstore.api.models.response.product.ProductResponse;
import com.softcaribbean.hulkstore.api.service.IProductService;
import com.softcaribbean.hulkstore.api.service.IUserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
    private final IUserService userService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ProductResponse> createProducto(Authentication authentication, @RequestBody CreateProductRequest productRequest) {
        User user = userService.findByEmail(authentication.getPrincipal().toString());
        var resp = productService.createProduct(productRequest, user);
        return ResponseEntity.status(HttpStatus.CREATED).body(productDomainResponseMapper.productToProductResponse(resp));
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
    public ResponseEntity<ProductResponse> addStock(
            Authentication authentication,
            @PathVariable Long idProduct,
            @RequestBody AddStockProduct addStockProduct) {
        User user = userService.findByEmail(authentication.getPrincipal().toString());
        var resp = productService.addProductStock(addStockProduct, user, idProduct);
        return ResponseEntity.status(HttpStatus.CREATED).body(productDomainResponseMapper.productToProductResponse(resp));
    }

    @PutMapping("/remove_stock/{idProduct}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ProductResponse> removeStock(
            Authentication authentication,
            @PathVariable Long idProduct,
            @RequestBody RemoveStockProduct removeStockProduct) {
        User user = userService.findByEmail(authentication.getPrincipal().toString());
        var resp = productService.removeProductStock(removeStockProduct, user, idProduct);
        return ResponseEntity.status(HttpStatus.CREATED).body(productDomainResponseMapper.productToProductResponse(resp));
    }
}
