package com.softcaribbean.hulkstore.api.controllers;

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

@RestController
@RequestMapping("/v1/product")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductController {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> createProducto() {
        return null;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> listAllProducts() {
        return null;
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
