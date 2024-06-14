package com.aey.papers_and_notes_api.product.domain.repositories;

import com.aey.papers_and_notes_api.product.domain.entities.Product;
import com.aey.papers_and_notes_api.product.domain.entities.ProductImage;
import com.aey.papers_and_notes_api.product.infrastructure.persistence.models.ProductJpa;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProductRepository {
    List<Product> findAllProducts(Integer limit, Integer offset);
    Integer countAllAvailableProducts();
    List<ProductImage> findAllProductImagesByProductId(UUID productId);
    Optional<ProductJpa> findOneProductById(UUID productId);
}
