package com.aey.papers_and_notes_api.product.domain.services;

import com.aey.papers_and_notes_api.common.error.ErrorCode;
import com.aey.papers_and_notes_api.product.domain.entities.Category;
import io.vavr.control.Either;


public interface CategoryService {
    Either<ErrorCode, Category> getCategoryById(Integer categoryId);
}
