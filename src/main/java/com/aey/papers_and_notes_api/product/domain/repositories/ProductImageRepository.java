package com.aey.papers_and_notes_api.product.domain.repositories;

import com.aey.papers_and_notes_api.product.domain.entities.ProductImage;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProductImageRepository {
    List<ProductImage> findAllProductImagesByProductId(UUID productId);
    Optional<ProductImage> findProductImageById(Integer productImageId);
}
