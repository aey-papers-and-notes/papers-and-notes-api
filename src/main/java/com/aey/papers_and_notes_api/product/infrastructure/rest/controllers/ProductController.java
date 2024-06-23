package com.aey.papers_and_notes_api.product.infrastructure.rest.controllers;

import com.aey.papers_and_notes_api.common.dtos.PaginationDto;
import com.aey.papers_and_notes_api.common.error.ErrorMapper;
import com.aey.papers_and_notes_api.common.response.ResponseCode;
import com.aey.papers_and_notes_api.common.response.ResponseCodeDto;
import com.aey.papers_and_notes_api.product.domain.services.ProductService;
import com.aey.papers_and_notes_api.product.infrastructure.rest.dtos.CreateProductDto;
import com.aey.papers_and_notes_api.product.infrastructure.rest.dtos.ProductDto;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

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
        var res = PaginationDto.<ProductDto>builder()
                .totalItems(paginationProducts.getTotalItems())
                .page(paginationProducts.getPage())
                .lastPage(paginationProducts.getLastPage())
                .items(products)
                .build();
        return ResponseEntity.ok(res);
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable UUID productId) {
        return productService.getProductById(productId)
                .map(ProductDto::fromEntity)
                .map(ResponseEntity::ok)
                .getOrElseGet(ErrorMapper::toResponse);
    }

    @PostMapping("/create")
    public ResponseEntity<ProductDto> createProduct(@Valid @RequestBody CreateProductDto createProductDto) {
        return productService.createProduct(createProductDto)
                .map(ProductDto::fromEntity)
                .map(ResponseEntity::ok)
                .getOrElseGet(ErrorMapper::toResponse);
    }

    @PatchMapping("/disable/{productId}")
    public ResponseEntity<ResponseCodeDto<ProductDto>> disableProduct(@PathVariable UUID productId) {
        return productService.disableProduct(productId)
                .map(ProductDto::fromEntity)
                .map(p -> ResponseEntity.ok(ResponseCodeDto.ok(ResponseCode.DISABLE_PRODUCT, p)))
                .getOrElseGet(ErrorMapper::toResponse);
    }

    @PatchMapping("/enable/{productId}")
    public ResponseEntity<ResponseCodeDto<ProductDto>> enableProduct(@PathVariable UUID productId) {
        return productService.enableProduct(productId)
                .map(ProductDto::fromEntity)
                .map(p -> ResponseEntity.ok(ResponseCodeDto.ok(ResponseCode.ENABLE_PRODUCT, p)))
                .getOrElseGet(ErrorMapper::toResponse);
    }
}
