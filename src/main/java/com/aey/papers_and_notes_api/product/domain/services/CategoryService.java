package com.aey.papers_and_notes_api.product.domain.services;

import com.aey.papers_and_notes_api.common.entities.Pagination;
import com.aey.papers_and_notes_api.common.error.ErrorCode;
import com.aey.papers_and_notes_api.product.domain.entities.Category;
import com.aey.papers_and_notes_api.product.infrastructure.rest.dtos.CreateCategoryDto;
import com.aey.papers_and_notes_api.product.infrastructure.rest.dtos.UpdateCategoryDto;
import io.vavr.control.Either;

import java.util.List;
import java.util.UUID;


public interface CategoryService {
    Pagination<Category> getAllCategories(Integer limit, Integer offset);
    Either<ErrorCode, Category> getCategoryById(Integer categoryId);
    List<Category> getAllCategoriesByProductId(UUID productId);
    Either<ErrorCode, Category> createCategory(CreateCategoryDto createCategoryDto);
    Either<ErrorCode, Category> updateCategory(Integer categoryId, UpdateCategoryDto category);
    Either<ErrorCode, Category> disableCategory(Integer categoryId);
    Either<ErrorCode, Category> enableCategory(Integer categoryId);
}
