package com.aey.papers_and_notes_api.product.application;

import com.aey.papers_and_notes_api.common.error.ErrorCode;
import com.aey.papers_and_notes_api.product.domain.entities.Category;
import com.aey.papers_and_notes_api.product.domain.repositories.CategoryRepository;
import com.aey.papers_and_notes_api.product.domain.services.CategoryService;
import io.vavr.control.Either;
import org.springframework.stereotype.Service;

@Service
public class CategoryUseCase implements CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryUseCase(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Either<ErrorCode, Category> getCategoryById(Integer categoryId) {
        return categoryRepository.findById(categoryId)
                .<Either<ErrorCode, Category>>map(Either::right)
                .orElseGet(() -> Either.left(ErrorCode.NOT_FOUND));
    }
}
