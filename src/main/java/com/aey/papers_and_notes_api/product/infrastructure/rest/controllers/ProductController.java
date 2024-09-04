package com.aey.papers_and_notes_api.product.infrastructure.rest.controllers;

import com.aey.papers_and_notes_api.common.dtos.PaginationDto;
import com.aey.papers_and_notes_api.common.error.ErrorMapper;
import com.aey.papers_and_notes_api.common.response.ResponseCode;
import com.aey.papers_and_notes_api.common.response.ResponseCodeDto;
import com.aey.papers_and_notes_api.common.response.ResponseCodeMapper;
import com.aey.papers_and_notes_api.product.domain.services.ProductService;
import com.aey.papers_and_notes_api.product.infrastructure.rest.dtos.*;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController()
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping()
    public ResponseEntity<PaginationDto<ProductDto>> getAllProducts(
            @RequestParam(required = false) Integer limit,
            @RequestParam(required = false) Integer offset
    ) {
        var paginationProducts = productService.getAllProducts(limit, offset);
        var products = paginationProducts.getItems().stream().map(ProductDto::fromEntity).toList();
        return ResponseEntity.ok(PaginationDto.fromEntity(paginationProducts, products));
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable UUID productId) {
        return productService.getProductById(productId)
                .map(ProductDto::fromEntity)
                .map(ResponseEntity::ok)
                .getOrElseGet(ErrorMapper::toResponse);
    }

    @PostMapping("/create")
    public ResponseEntity<ProductDto> createProduct(
            @Valid @RequestBody CreateProductDto createProductDto
    ) {
        return productService.createProduct(createProductDto)
                .map(ProductDto::fromEntity)
                .map(ResponseEntity::ok)
                .getOrElseGet(ErrorMapper::toResponse);
    }

    @PatchMapping("/{productId}")
    public ResponseEntity<ResponseCodeDto<ProductDto>> updateProduct(
            @PathVariable UUID productId,
            @Valid @RequestBody UpdateProductDto updateProductDto
    ) {
        return productService.updateProduct(productId, updateProductDto)
                .map(ProductDto::fromEntity)
                .map(p -> ResponseCodeMapper.toResponse(ResponseCode.CREATE_PRODUCT, p))
                .getOrElseGet(ErrorMapper::toResponse);
    }

    @PatchMapping("/disable/{productId}")
    public ResponseEntity<ResponseCodeDto<ProductDto>> disableProduct(@PathVariable UUID productId) {
        return productService.disableProduct(productId)
                .map(ProductDto::fromEntity)
                .map(p -> ResponseCodeMapper.toResponse(ResponseCode.DISABLE_PRODUCT, p))
                .getOrElseGet(ErrorMapper::toResponse);
    }

    @PatchMapping("/enable/{productId}")
    public ResponseEntity<ResponseCodeDto<ProductDto>> enableProduct(@PathVariable UUID productId) {
        return productService.enableProduct(productId)
                .map(ProductDto::fromEntity)
                .map(p -> ResponseCodeMapper.toResponse(ResponseCode.ENABLE_PRODUCT, p))
                .getOrElseGet(ErrorMapper::toResponse);
    }

    @PostMapping("/{productId}/images")
    public ResponseEntity<ResponseCodeDto<ProductImageDto>> uploadProductImage(
            @PathVariable UUID productId,
            @Valid @RequestBody UploadProductImageDto uploadProductImageDto
    ) {
        return productService.uploadProductImage(productId, uploadProductImageDto)
                .map(ProductImageDto::fromEntity)
                .map(p -> ResponseCodeMapper.toResponse(ResponseCode.PRODUCT_IMAGE_UPLOADED, p))
                .getOrElseGet(ErrorMapper::toResponse);
    }

    @DeleteMapping("/{productId}/images/{imageId}")
    public ResponseEntity<ResponseCodeDto<ProductImageDto>> deleteProductImage(
            @PathVariable UUID productId,
            @PathVariable Integer imageId
    ) {
        return productService.deleteProductImage(productId, imageId)
                .map(ProductImageDto::fromEntity)
                .map((i) -> ResponseCodeMapper.toResponse(ResponseCode.PRODUCT_IMAGE_DELETED, i))
                .getOrElseGet(ErrorMapper::toResponse);
    }

    @GetMapping("/{productId}/categories")
    public ResponseEntity<List<CategoryDto>> getCategoriesByProductId(@PathVariable UUID productId) {
        return productService.getAllCategoriesByProductId(productId)
                .map(categories -> categories.stream().map(CategoryDto::fromEntity).collect(Collectors.toList()))
                .map(ResponseEntity::ok)
                .getOrElseGet(ErrorMapper::toResponse);
    }

    @PostMapping("/{productId}/categories/add")
    public ResponseEntity<ResponseCodeDto<ProductDto>> addCategoriesToProduct(
            @PathVariable UUID productId,
            @Valid @RequestBody ProductCategoryAssociationDto productCategoryAssociationDto
    ) {
        return productService.addCategoriesToProduct(productId, productCategoryAssociationDto)
                .map(ProductDto::fromEntity)
                .map((p) -> ResponseCodeMapper.toResponse(ResponseCode.PRODUCT_CATEGORY_ASSOCIATION, p))
                .getOrElseGet(ErrorMapper::toResponse);
    }

    @PostMapping("/{productId}/categories/remove")
    public ResponseEntity<ResponseCodeDto<ProductDto>> removeCategoriesFromProduct(
            @PathVariable UUID productId,
            @Valid @RequestBody ProductCategoryAssociationDto productCategoryAssociationDto
    ) {
        return productService.removeCategoriesToProduct(productId, productCategoryAssociationDto)
                .map(ProductDto::fromEntity)
                .map((p) -> ResponseCodeMapper.toResponse(ResponseCode.PRODUCT_CATEGORY_REMOVE, p))
                .getOrElseGet(ErrorMapper::toResponse);
    }
}
