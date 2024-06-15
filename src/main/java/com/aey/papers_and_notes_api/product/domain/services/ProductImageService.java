package com.aey.papers_and_notes_api.product.domain.services;


import com.aey.papers_and_notes_api.common.error.ErrorCode;
import com.aey.papers_and_notes_api.product.domain.entities.ProductImage;
import io.vavr.control.Either;

import java.util.List;
import java.util.UUID;

public interface ProductImageService {
    Either<ErrorCode, List<ProductImage>> getAllProductImagesByProductId(UUID productId);
    Either<ErrorCode, ProductImage> uploadProductImageURL(UUID productId, String url);
    Either<ErrorCode, ProductImage> updateProductImageURL(UUID productId, String url);
    Either<ErrorCode, ProductImage> deleteProductImageURL(Integer id, UUID productId);
}
