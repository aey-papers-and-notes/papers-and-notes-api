package com.aey.papers_and_notes_api.product.domain.services;

import com.aey.papers_and_notes_api.common.entities.Pagination;
import com.aey.papers_and_notes_api.common.error.ErrorCode;
import com.aey.papers_and_notes_api.product.domain.entities.Category;
import com.aey.papers_and_notes_api.product.domain.entities.Product;
import com.aey.papers_and_notes_api.product.domain.entities.ProductImage;
import com.aey.papers_and_notes_api.product.infrastructure.rest.dtos.CreateProductDto;
import com.aey.papers_and_notes_api.product.infrastructure.rest.dtos.UpdateProductDto;
import com.aey.papers_and_notes_api.product.infrastructure.rest.dtos.UploadProductImageDto;
import io.vavr.control.Either;

import java.util.Set;
import java.util.UUID;

public interface ProductService {
    Pagination<Product> getAllProducts(Integer limit, Integer offset);
    Either<ErrorCode, Product> getProductById(UUID productId);
    Either<ErrorCode, Product> createProduct(CreateProductDto createProductDto);
    Either<ErrorCode, Product> updateProduct(UUID productId, UpdateProductDto updateProductDto);
    Either<ErrorCode, Product> disableProduct(UUID productId);
    Either<ErrorCode, Product> enableProduct(UUID productId);
    Either<ErrorCode, ProductImage> uploadProductImage(UUID productId, UploadProductImageDto productImage);
    Either<ErrorCode, ProductImage> deleteProductImage(UUID productId, Integer imageId);
    Either<ErrorCode, Set<Category>> getAllCategoriesByProductId(UUID productId);
}
