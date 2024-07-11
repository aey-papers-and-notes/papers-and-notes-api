package com.aey.papers_and_notes_api.product.domain.services;

import com.aey.papers_and_notes_api.common.error.ErrorCode;
import com.aey.papers_and_notes_api.product.domain.entities.Category;
import com.aey.papers_and_notes_api.product.infrastructure.rest.dtos.UpdateCategoryDto;
import io.vavr.control.Either;

import java.util.Set;
import java.util.UUID;


public interface CategoryService {
    Set<Category> getAllCategories();
    Either<ErrorCode, Category> getCategoryById(Integer categoryId);
    Set<Category> getAllCategoriesByProductId(UUID productId);
    Either<ErrorCode, Category> updateCategory(Integer categoryId, UpdateCategoryDto category);
}
