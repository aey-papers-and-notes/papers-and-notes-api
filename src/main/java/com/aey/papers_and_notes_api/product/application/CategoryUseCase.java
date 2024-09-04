package com.aey.papers_and_notes_api.product.application;

import com.aey.papers_and_notes_api.common.entities.Pagination;
import com.aey.papers_and_notes_api.common.error.ErrorCode;
import com.aey.papers_and_notes_api.product.domain.entities.Category;
import com.aey.papers_and_notes_api.product.domain.repositories.CategoryRepository;
import com.aey.papers_and_notes_api.product.domain.services.CategoryService;
import com.aey.papers_and_notes_api.product.infrastructure.rest.dtos.CreateCategoryDto;
import com.aey.papers_and_notes_api.product.infrastructure.rest.dtos.UpdateCategoryDto;
import io.vavr.control.Either;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CategoryUseCase implements CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryUseCase(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Pagination<Category> getAllCategories(Integer limit, Integer offset) {
        limit = limit == null ? 10 : limit;
        offset = offset == null ? 0 : offset;
        var categories = categoryRepository.findAllCategories(limit, offset).stream().toList();
        Integer totalCategories = categoryRepository.countAllAvailableCategories();
        return Pagination.<Category>builder()
                .totalItems(totalCategories)
                .lastPage(Pagination.calcLastPage(limit, totalCategories))
                .page(Pagination.calcPage(limit, offset))
                .items(categories)
                .build();
    }

    @Override
    public Either<ErrorCode, Category> getCategoryById(Integer categoryId) {
        var category = categoryRepository.findById(categoryId);
        if (category.isEmpty()) {
            return Either.left(ErrorCode.CATEGORY_NOT_FOUND);
        }
        if (category.get().getIsActive().equals(Boolean.FALSE)) {
            return Either.left(ErrorCode.CATEGORY_NOT_AVAILABLE);
        }
        return Either.right(category.get());
    }

    @Override
    public List<Category> getAllCategoriesByProductId(UUID productId) {
        return categoryRepository.findAllCategoriesByProductId(productId);
    }

    @Override
    @Transactional
    public Either<ErrorCode, Category> createCategory(CreateCategoryDto createCategoryDto) {
        var category = Category.builder()
                .name(createCategoryDto.getName())
                .description(createCategoryDto.getDescription())
                .isActive(Boolean.TRUE)
                .createdAt(new Date())
                .updatedAt(new Date())
                .build();
        return categoryRepository.createCategory(category)
                .<Either<ErrorCode, Category>>map(Either::right)
                .orElseGet(() -> Either.left(ErrorCode.CATEGORY_ERROR));
    }

    @Override
    @Transactional
    public Either<ErrorCode, Category> updateCategory(Integer categoryId, UpdateCategoryDto category) {
        var categoryFound = getCategoryById(categoryId);
        if (categoryFound.isLeft()) {
            return Either.left(categoryFound.getLeft());
        }
        Category categoryToUpdate = Category.builder()
                .categoryId(categoryFound.get().getCategoryId())
                .name(category.getName() != null ? category.getName() : categoryFound.get().getName())
                .description(category.getDescription() != null ? category.getDescription() : categoryFound.get().getDescription())
                .isActive(Boolean.TRUE)
                .createdAt(categoryFound.get().getCreatedAt())
                .updatedAt(new Date())
                .build();

        return categoryRepository.saveCategoryById(categoryToUpdate)
                .<Either<ErrorCode, Category>>map(Either::right)
                .orElseGet(() -> Either.left(ErrorCode.ERROR));
    }

    @Override
    @Transactional
    public Either<ErrorCode, Category> disableCategory(Integer categoryId) {
        var category = getCategoryById(categoryId);
        if (category.isLeft()) {
            return Either.left(category.getLeft());
        }
        Category categoryUpdate = category.get();
        categoryUpdate.setIsActive(Boolean.FALSE);
        categoryUpdate.setUpdatedAt(new Date());
        return categoryRepository.updateCategoryState(categoryUpdate)
                .<Either<ErrorCode, Category>>map(Either::right)
                .orElseGet(() -> Either.left(ErrorCode.CATEGORY_ERROR));
    }

    @Override
    @Transactional
    public Either<ErrorCode, Category> enableCategory(Integer categoryId) {
        var category = categoryRepository.findById(categoryId);
        if (category.isEmpty()) {
            return Either.left(ErrorCode.CATEGORY_NOT_FOUND);
        }
        Category categoryUpdate = category.get();
        categoryUpdate.setIsActive(Boolean.TRUE);
        categoryUpdate.setUpdatedAt(new Date());
        return categoryRepository.updateCategoryState(categoryUpdate)
                .<Either<ErrorCode, Category>>map(Either::right)
                .orElseGet(() -> Either.left(ErrorCode.CATEGORY_ERROR));
    }


}
