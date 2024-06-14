package com.aey.papers_and_notes_api.product.application;

import com.aey.papers_and_notes_api.common.dtos.PaginationDto;
import com.aey.papers_and_notes_api.common.error.ErrorCode;
import com.aey.papers_and_notes_api.product.domain.entities.ProductImage;
import com.aey.papers_and_notes_api.product.domain.repositories.ProductRepository;
import com.aey.papers_and_notes_api.product.domain.services.ProductService;
import com.aey.papers_and_notes_api.product.infrastructure.persistence.models.ProductJpa;
import com.aey.papers_and_notes_api.product.infrastructure.rest.dto.ProductDto;
import io.vavr.control.Either;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProductUseCase implements ProductService {

    private final ProductRepository productRepository;

    ProductUseCase(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public PaginationDto<ProductDto> getAllProducts(Integer limit, Integer offset) {
        limit = limit == null ? 10 : limit;
        offset = offset == null ? 0 : offset;
        List<ProductDto> products = productRepository.findAllProducts(limit, offset)
                .stream()
                .map(product -> {
                    List<ProductImage> productImages = productRepository.findAllProductImagesByProductId(product.getProductId());
                    return ProductDto.builder()
                            .productId(product.getProductId())
                            .name(product.getName())
                            .description(product.getDescription())
                            .stock(product.getStock())
                            .price(product.getPrice())
                            .createdAt(product.getCreatedAt())
                            .updatedAt(product.getUpdatedAt())
                            .isActive(product.getIsActive())
                            .brandId(product.getBrandId())
                            .productImages(productImages)
                            .build();
                })
                .toList();
        Integer totalItems = productRepository.countAllAvailableProducts();
        return PaginationDto.<ProductDto>builder()
                .totalItems(totalItems)
                .lastPage(PaginationDto.calcLastPage(limit, totalItems))
                .page(PaginationDto.calcPage(limit, offset))
                .items(products)
                .build();
    }

    @Override
    public Either<ErrorCode, ProductDto> getProductById(UUID productId) {
        Optional<ProductJpa> product = productRepository.findOneProductById(productId);

        if (product.isEmpty()) {
            return Either.left(ErrorCode.NOT_FOUND);
        }
        if (product.get().getIsActive().equals(Boolean.FALSE)) {
            return Either.left(ErrorCode.RESOURCE_NOT_AVAILABLE);
        }

        @SuppressWarnings("OptionalGetWithoutIsPresent")
        ProductDto productFound = product.map(p -> ProductDto.fromEntity(product.get().toEntity())).get();
        return Either.right(productFound);
    }
}
