package com.aey.papers_and_notes_api.product.domain.services;

import com.aey.papers_and_notes_api.common.error.ErrorCode;
import com.aey.papers_and_notes_api.product.infrastructure.rest.dto.ProductDto;
import io.vavr.control.Either;

import java.util.List;
import java.util.UUID;

public interface ProductService {
    Either<ErrorCode, List<ProductDto>> getAllProducts();
    Either<ErrorCode, ProductDto> getProductById(UUID productId);
}
