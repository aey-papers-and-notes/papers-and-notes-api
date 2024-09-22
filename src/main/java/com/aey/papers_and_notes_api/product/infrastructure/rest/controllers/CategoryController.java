package com.aey.papers_and_notes_api.product.infrastructure.rest.controllers;

import com.aey.papers_and_notes_api.common.dtos.PaginationDto;
import com.aey.papers_and_notes_api.common.error.ErrorDto;
import com.aey.papers_and_notes_api.common.error.ErrorMapper;
import com.aey.papers_and_notes_api.common.response.ResponseCode;
import com.aey.papers_and_notes_api.common.response.ResponseCodeDto;
import com.aey.papers_and_notes_api.common.response.ResponseCodeMapper;
import com.aey.papers_and_notes_api.product.domain.services.CategoryService;
import com.aey.papers_and_notes_api.product.infrastructure.rest.dtos.CategoryDto;
import com.aey.papers_and_notes_api.product.infrastructure.rest.dtos.CreateCategoryDto;
import com.aey.papers_and_notes_api.product.infrastructure.rest.dtos.ProductDto;
import com.aey.papers_and_notes_api.product.infrastructure.rest.dtos.UpdateCategoryDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @Operation(summary = "Get all categories")
    @ApiResponse(
            responseCode = "200",
            description = "Get all categories",
            content = { @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ProductDto.class)) }
    )
    @ApiResponse(
            responseCode = "400",
            description = "Resource not available",
            content = { @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorDto.class)) }
    )
    @ApiResponse(
            responseCode = "404",
            description = "Resource not found",
            content = { @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorDto.class)) }
    )
    @GetMapping
    public ResponseEntity<PaginationDto<CategoryDto>> getAllCategories(
            @RequestParam(required = false) Integer limit,
            @RequestParam(required = false) Integer offset
    ) {
        var paginationCategories = categoryService.getAllCategories(limit, offset);
        var categories = paginationCategories.getItems().stream().map(CategoryDto::fromEntity).toList();
        return ResponseEntity.ok(PaginationDto.fromEntity(paginationCategories, categories));
    }

    @Operation(summary = "Get category by id")
    @ApiResponse(
            responseCode = "200",
            description = "Get category by id",
            content = { @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ProductDto.class)) }
    )
    @ApiResponse(
            responseCode = "400",
            description = "Resource not available",
            content = { @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorDto.class)) }
    )
    @ApiResponse(
            responseCode = "404",
            description = "Resource not found",
            content = { @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorDto.class)) }
    )
    @GetMapping("/{categoryId}")
    public ResponseEntity<CategoryDto> getCategoryById(@PathVariable Integer categoryId) {
        return categoryService.getCategoryById(categoryId)
                .map(CategoryDto::fromEntity)
                .map(ResponseEntity::ok)
                .getOrElseGet(ErrorMapper::toResponse);
    }

    @Operation(summary = "Create category")
    @ApiResponse(
            responseCode = "201",
            description = "Create category",
            content = { @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ProductDto.class)) }
    )
    @ApiResponse(
            responseCode = "400",
            description = "Resource not available",
            content = { @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorDto.class)) }
    )
    @ApiResponse(
            responseCode = "404",
            description = "Resource not found",
            content = { @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorDto.class)) }
    )
    @PostMapping()
    public ResponseEntity<ResponseCodeDto<CategoryDto>> createCategory(
            @Valid @RequestBody CreateCategoryDto createCategoryDto
    ) {
        return categoryService.createCategory(createCategoryDto)
                .map(CategoryDto::fromEntity)
                .map((c) -> ResponseCodeMapper.toResponse(ResponseCode.CREATE_CATEGORY, c))
                .getOrElseGet(ErrorMapper::toResponse);
    }

    @Operation(summary = "Update category")
    @ApiResponse(
            responseCode = "200",
            description = "Update category",
            content = { @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ProductDto.class)) }
    )
    @ApiResponse(
            responseCode = "400",
            description = "Resource not available",
            content = { @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorDto.class)) }
    )
    @ApiResponse(
            responseCode = "404",
            description = "Resource not found",
            content = { @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorDto.class)) }
    )
    @PatchMapping("/{categoryId}")
    public ResponseEntity<ResponseCodeDto<CategoryDto>> updateCategory(@PathVariable Integer categoryId, @RequestBody UpdateCategoryDto categoryDto) {
        return categoryService.updateCategory(categoryId, categoryDto)
                .map(CategoryDto::fromEntity)
                .map((c) -> ResponseCodeMapper.toResponse(ResponseCode.UPDATE_CATEGORY, c))
                .getOrElseGet(ErrorMapper::toResponse);
    }

    @Operation(summary = "Disable category")
    @ApiResponse(
            responseCode = "200",
            description = "Disable category",
            content = { @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ProductDto.class)) }
    )
    @ApiResponse(
            responseCode = "400",
            description = "Resource not available",
            content = { @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorDto.class)) }
    )
    @ApiResponse(
            responseCode = "404",
            description = "Resource not found",
            content = { @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorDto.class)) }
    )
    @PatchMapping("/disable/{categoryId}")
    public ResponseEntity<ResponseCodeDto<CategoryDto>> disableCategory(@PathVariable Integer categoryId) {
        return categoryService.disableCategory(categoryId)
                .map(CategoryDto::fromEntity)
                .map((c) -> ResponseCodeMapper.toResponse(ResponseCode.DISABLE_CATEGORY, c))
                .getOrElseGet(ErrorMapper::toResponse);
    }

    @Operation(summary = "Enable category")
    @ApiResponse(
            responseCode = "200",
            description = "Enable category",
            content = { @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ProductDto.class)) }
    )
    @ApiResponse(
            responseCode = "400",
            description = "Resource not available",
            content = { @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorDto.class)) }
    )
    @ApiResponse(
            responseCode = "404",
            description = "Resource not found",
            content = { @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorDto.class)) }
    )
    @PatchMapping("/enable/{categoryId}")
    public ResponseEntity<ResponseCodeDto<CategoryDto>> enableCategory(@PathVariable Integer categoryId) {
        return categoryService.enableCategory(categoryId)
                .map(CategoryDto::fromEntity)
                .map((c) -> ResponseCodeMapper.toResponse(ResponseCode.DISABLE_CATEGORY, c))
                .getOrElseGet(ErrorMapper::toResponse);
    }
}
