package com.aey.papers_and_notes_api.product.infrastructure.rest.controllers;

import com.aey.papers_and_notes_api.common.dtos.PaginationDto;
import com.aey.papers_and_notes_api.product.domain.services.ProductService;
import com.aey.papers_and_notes_api.product.infrastructure.rest.dto.ProductDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/products")
    public ResponseEntity<PaginationDto<ProductDto>> getAllProducts(
            @RequestParam(required = false) Integer limit,
            @RequestParam(required = false) Integer offset
    ) {
        return ResponseEntity.ok(productService.getAllProducts(limit, offset));
    }
}
