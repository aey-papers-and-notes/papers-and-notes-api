package com.aey.papers_and_notes_api.product.application;

import com.aey.papers_and_notes_api.common.error.ErrorCode;
import com.aey.papers_and_notes_api.product.domain.entities.Category;
import com.aey.papers_and_notes_api.product.domain.repositories.CategoryRepository;
import com.aey.papers_and_notes_api.product.domain.services.CategoryService;
import io.vavr.control.Either;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CategoryUseCase implements CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryUseCase(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
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
}
