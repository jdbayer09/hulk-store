package com.softcaribbean.hulkstore.api.service.impl;

import com.softcaribbean.hulkstore.api.exceptions.NotProductException;
import com.softcaribbean.hulkstore.api.models.domain.Product;
import com.softcaribbean.hulkstore.api.models.domain.User;
import com.softcaribbean.hulkstore.api.models.entity.ProductEntity;
import com.softcaribbean.hulkstore.api.models.entity.ProductStockEntity;
import com.softcaribbean.hulkstore.api.models.mapper.product.ProductEntityDomainMapper;
import com.softcaribbean.hulkstore.api.models.mapper.user.UserEntityDomainMapper;
import com.softcaribbean.hulkstore.api.models.request.product.AddStockProduct;
import com.softcaribbean.hulkstore.api.models.request.product.CreateProductRequest;
import com.softcaribbean.hulkstore.api.models.request.product.RemoveStockProduct;
import com.softcaribbean.hulkstore.api.repository.IProductRepository;
import com.softcaribbean.hulkstore.api.repository.IProductStockRepository;
import com.softcaribbean.hulkstore.api.service.IProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements IProductService {

    private final IProductStockRepository productStockRepository;
    private final IProductRepository productRepository;
    private final ProductEntityDomainMapper productEntityDomainMapper;
    private final UserEntityDomainMapper userEntityDomainMapper;

    @Override
    @Transactional(readOnly = true)
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

    @Override
    @Transactional
    public Product createProduct(CreateProductRequest product, User user) {
        var newProduct = new ProductEntity();
        newProduct.setName(product.name());
        newProduct.setType(product.type());
        newProduct.setPrice(product.price());
        newProduct.setTotalPieces(product.quantityPieces());


        var productResp = productRepository.save(newProduct);
        productResp.setTransactions(new ArrayList<>());

        var productTransaction = new ProductStockEntity();
        productTransaction.setProduct(productResp);
        productTransaction.setQuantity(product.quantityPieces());
        productTransaction.setUser(userEntityDomainMapper.userToUserEntity(user));

        productResp.getTransactions().add(productStockRepository.save(productTransaction));
        return productEntityDomainMapper.productEntityToProduct(productResp);
    }

    @Override
    @Transactional
    public Product addProductStock(AddStockProduct addStockProduct, User user, Long productId) {
        return addNewTransaction(addStockProduct.quantity(), user, productId, false);
    }

    @Override
    @Transactional
    public Product removeProductStock(RemoveStockProduct removeStockProduct, User user, Long productId) {
        return addNewTransaction(removeStockProduct.quantity(), user, productId, true);
    }

    private Product addNewTransaction(int quantity, User user, Long productId, boolean isRemove) {
        var product = productRepository.findById(productId).orElseThrow(() -> new NotProductException(productId, "No existe el producto"));
        var productTransaction = new ProductStockEntity();
        productTransaction.setProduct(product);
        productTransaction.setUser(userEntityDomainMapper.userToUserEntity(user));
        if (quantity <= 0) {
            throw  new NotProductException(productId, "La cantidad no puede ser menor o igual a 0. ");
        } else {
            if (isRemove) {
                productTransaction.setQuantity(quantity * -1);
            } else {
                productTransaction.setQuantity(quantity);
            }
        }
        if (isRemove) {
            var quantityRemove = product.getTotalPieces() + (quantity * -1);
            if (quantityRemove < 0) {
                throw  new NotProductException(quantityRemove, "No se puede solicitar mas productos de los que hay en inventario: " + product.getTotalPieces());
            }
        }
        productStockRepository.save(productTransaction);
        if (isRemove) {
            product.setTotalPieces(product.getTotalPieces() + (quantity * -1));
        } else {
            product.setTotalPieces(product.getTotalPieces() + quantity);
        }
        product = productRepository.save(product);
        return productEntityDomainMapper.productEntityToProduct(product);
    }
}
