package com.aey.papers_and_notes_api.product.domain.repositories;

import com.aey.papers_and_notes_api.product.domain.entities.Product;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProductRepository {
    List<Product> findAllProducts(Integer limit, Integer offset);
    Integer countAllAvailableProducts();
    Optional<Product> findOneProductById(UUID productId);
    Optional<Product> createProduct(Product product);
    Optional<Product> updateProductState(Product product);
}
