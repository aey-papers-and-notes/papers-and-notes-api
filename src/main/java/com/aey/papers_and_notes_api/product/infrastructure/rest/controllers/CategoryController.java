package com.aey.papers_and_notes_api.product.infrastructure.rest.controllers;

import com.aey.papers_and_notes_api.common.dtos.PaginationDto;
import com.aey.papers_and_notes_api.common.error.ErrorMapper;
import com.aey.papers_and_notes_api.common.response.ResponseCode;
import com.aey.papers_and_notes_api.common.response.ResponseCodeDto;
import com.aey.papers_and_notes_api.common.response.ResponseCodeMapper;
import com.aey.papers_and_notes_api.product.domain.services.CategoryService;
import com.aey.papers_and_notes_api.product.infrastructure.rest.dtos.CategoryDto;
import com.aey.papers_and_notes_api.product.infrastructure.rest.dtos.CreateCategoryDto;
import com.aey.papers_and_notes_api.product.infrastructure.rest.dtos.UpdateCategoryDto;
import jakarta.validation.Valid;
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
    public ResponseEntity<PaginationDto<CategoryDto>> getAllCategories(
            @RequestParam(required = false) Integer limit,
            @RequestParam(required = false) Integer offset
    ) {
        var paginationCategories = categoryService.getAllCategories(limit, offset);
        var categories = paginationCategories.getItems().stream().map(CategoryDto::fromEntity).toList();
        return ResponseEntity.ok(PaginationDto.fromEntity(paginationCategories, categories));
    }

    @GetMapping("/{categoryId}")
    public ResponseEntity<CategoryDto> getCategoryById(@PathVariable Integer categoryId) {
        return categoryService.getCategoryById(categoryId)
                .map(CategoryDto::fromEntity)
                .map(ResponseEntity::ok)
                .getOrElseGet(ErrorMapper::toResponse);
    }

    @PostMapping()
    public ResponseEntity<ResponseCodeDto<CategoryDto>> createCategory(
            @Valid @RequestBody CreateCategoryDto createCategoryDto
    ) {
        return categoryService.createCategory(createCategoryDto)
                .map(CategoryDto::fromEntity)
                .map((c) -> ResponseCodeMapper.toResponse(ResponseCode.CREATE_CATEGORY, c))
                .getOrElseGet(ErrorMapper::toResponse);
    }

    @PatchMapping("/{categoryId}")
    public ResponseEntity<ResponseCodeDto<CategoryDto>> updateCategory(@PathVariable Integer categoryId, @RequestBody UpdateCategoryDto categoryDto) {
        return categoryService.updateCategory(categoryId, categoryDto)
                .map(CategoryDto::fromEntity)
                .map((c) -> ResponseCodeMapper.toResponse(ResponseCode.UPDATE_CATEGORY, c))
                .getOrElseGet(ErrorMapper::toResponse);
    }

    @PatchMapping("/disable/{categoryId}")
    public ResponseEntity<ResponseCodeDto<CategoryDto>> disableCategory(@PathVariable Integer categoryId) {
        return categoryService.disableCategory(categoryId)
                .map(CategoryDto::fromEntity)
                .map((c) -> ResponseCodeMapper.toResponse(ResponseCode.DISABLE_CATEGORY, c))
                .getOrElseGet(ErrorMapper::toResponse);
    }

    @PatchMapping("/enable/{categoryId}")
    public ResponseEntity<ResponseCodeDto<CategoryDto>> enableCategory(@PathVariable Integer categoryId) {
        return categoryService.enableCategory(categoryId)
                .map(CategoryDto::fromEntity)
                .map((c) -> ResponseCodeMapper.toResponse(ResponseCode.DISABLE_CATEGORY, c))
                .getOrElseGet(ErrorMapper::toResponse);
    }
}
