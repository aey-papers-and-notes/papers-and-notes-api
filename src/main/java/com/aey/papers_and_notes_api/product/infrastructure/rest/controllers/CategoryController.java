package com.aey.papers_and_notes_api.product.infrastructure.rest.controllers;

import com.aey.papers_and_notes_api.common.error.ErrorMapper;
import com.aey.papers_and_notes_api.product.domain.services.CategoryService;
import com.aey.papers_and_notes_api.product.infrastructure.rest.dtos.CategoryDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public ResponseEntity<Set<CategoryDto>> getAllCategories() {
        return ResponseEntity.ok(
                categoryService.getAllCategories()
                        .stream()
                        .map(CategoryDto::fromEntity)
                        .collect(Collectors.toSet())
        );
    }

    @GetMapping("/{categoryId}")
    public ResponseEntity<CategoryDto> getCategoryById(@PathVariable Integer categoryId) {
        return categoryService.getCategoryById(categoryId)
                .map(CategoryDto::fromEntity)
                .map(ResponseEntity::ok)
                .getOrElseGet(ErrorMapper::toResponse);
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<Set<CategoryDto>> getCategoriesByProductId(@PathVariable UUID productId) {
        return ResponseEntity.ok(
                categoryService.getAllCategoriesByProductId(productId)
                        .stream()
                        .map(CategoryDto::fromEntity)
                        .collect(Collectors.toSet())
        );
    }
}
