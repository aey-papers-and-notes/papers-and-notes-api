package com.aey.papers_and_notes_api.product.application;

import com.aey.papers_and_notes_api.common.error.ErrorCode;
import com.aey.papers_and_notes_api.product.domain.repositories.ProductRepository;
import com.aey.papers_and_notes_api.product.domain.services.ProductService;
import com.aey.papers_and_notes_api.product.infrastructure.persistence.models.ProductJpa;
import com.aey.papers_and_notes_api.product.infrastructure.rest.dto.ProductDto;
import io.vavr.control.Either;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ProductUseCase implements ProductService {

    @Autowired
    private final ProductRepository productRepository;

    ProductUseCase(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Either<ErrorCode, List<ProductDto>> getAllProducts() {
        List<ProductDto> products = productRepository.findAllProducts()
                .stream()
                .map(ProductJpa::toEntity)
                .map(ProductDto::fromEntity)
                .toList();
        if (products.isEmpty()) {
            return Either.left(ErrorCode.NOT_FOUND);
        }
        return Either.right(products);
    }

    @Override
    public Either<ErrorCode, ProductDto> getProductById(UUID productId) {
        return null;
    }
}
