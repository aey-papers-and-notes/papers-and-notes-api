package com.aey.papers_and_notes_api.product.application;

import com.aey.papers_and_notes_api.common.error.ErrorCode;
import com.aey.papers_and_notes_api.product.domain.entities.Category;
import com.aey.papers_and_notes_api.product.domain.repositories.CategoryRepository;
import com.aey.papers_and_notes_api.product.domain.services.CategoryService;
import com.aey.papers_and_notes_api.product.infrastructure.rest.dtos.UpdateCategoryDto;
import io.vavr.control.Either;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Service
public class CategoryUseCase implements CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryUseCase(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Set<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public Either<ErrorCode, Category> getCategoryById(Integer categoryId) {
        Optional<Category> category = categoryRepository.findById(categoryId);

        if (category.isEmpty()) {
            return Either.left(ErrorCode.CATEGORY_NOT_FOUND);
        }
        if (category.get().getIsActive().equals(Boolean.FALSE)) {
            return Either.left(ErrorCode.CATEGORY_NOT_AVAILABLE);
        }

        return Either.right(category.get());
    }

    @Override
    public Set<Category> getAllCategoriesByProductId(UUID productId) {
        return categoryRepository.findAllCategoriesByProductId(productId);
    }

    @Override
    public Either<ErrorCode, Category> updateCategory(Integer categoryId, UpdateCategoryDto category) {
        var categoryFound = getCategoryById(categoryId);
        if (categoryFound.isLeft()) {
            return Either.left(categoryFound.getLeft());
        }
        Category categoryToUpdate = Category.builder()
                .categoryId(categoryFound.get().getCategoryId())
                .name(category.getName())
                .isActive(Boolean.TRUE)
                .createdAt(categoryFound.get().getCreatedAt())
                .updatedAt(new Date())
                .build();
        categoryRepository
        return null;
    }


}
