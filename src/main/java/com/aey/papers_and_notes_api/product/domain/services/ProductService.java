package com.aey.papers_and_notes_api.product.domain.services;

import com.aey.papers_and_notes_api.common.dtos.PaginationDto;
import com.aey.papers_and_notes_api.common.error.ErrorCode;
import com.aey.papers_and_notes_api.product.infrastructure.rest.dto.ProductDto;
import io.vavr.control.Either;

import java.util.UUID;

public interface ProductService {
    PaginationDto<ProductDto> getAllProducts(Integer limit, Integer offset);
    Either<ErrorCode, ProductDto> getProductById(UUID productId);
}
