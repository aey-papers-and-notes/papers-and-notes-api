package com.aey.papers_and_notes_api.product.infrastructure.rest.controllers;

import com.aey.papers_and_notes_api.common.dtos.PaginationDto;
import com.aey.papers_and_notes_api.common.error.ErrorMapper;
import com.aey.papers_and_notes_api.product.domain.entities.Product;
import com.aey.papers_and_notes_api.product.domain.services.ProductService;
import com.aey.papers_and_notes_api.product.infrastructure.rest.dto.ProductDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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
        return ResponseEntity.ok(productService.getAllProducts(limit, offset));
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable UUID productId) {
        return productService.getProductById(productId)
                .map(ResponseEntity::ok)
                .getOrElseGet(ErrorMapper::toResponse);
    }
}
