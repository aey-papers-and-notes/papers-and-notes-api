package com.aey.papers_and_notes_api.product.domain.services;


import com.aey.papers_and_notes_api.common.error.ErrorCode;
import com.aey.papers_and_notes_api.product.domain.entities.ProductImage;
import com.aey.papers_and_notes_api.product.infrastructure.rest.dtos.UploadProductImageDto;
import io.vavr.control.Either;

import java.util.List;
import java.util.UUID;

public interface ProductImageService {
    List<ProductImage> getAllProductImagesByProductId(UUID productId);
    Either<ErrorCode, ProductImage> saveProductImage(ProductImage productImage);
    Either<ErrorCode, ProductImage> uploadProductImage(UUID productId, UploadProductImageDto uploadProductImageDto);
    Either<ErrorCode, ProductImage> deleteProductImageURL(Integer id, UUID productId);
}
