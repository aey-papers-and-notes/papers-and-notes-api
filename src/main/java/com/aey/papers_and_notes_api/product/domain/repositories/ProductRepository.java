package com.aey.papers_and_notes_api.product.domain.repositories;

import com.aey.papers_and_notes_api.product.persistence.models.ProductJpa;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProductRepository {
    List<ProductJpa> findAllProducts();
    Optional<ProductJpa> findOneProductById(UUID productId);
    Optional<ProductJpa> createProduct(ProductJpa product);
    Optional<ProductJpa> updateProduct(ProductJpa product);
    void deleteProduct(UUID productId);
    void disableProduct(ProductJpa product);
}
