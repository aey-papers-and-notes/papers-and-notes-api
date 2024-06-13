package com.aey.papers_and_notes_api.product.application;

import com.aey.papers_and_notes_api.common.dtos.PaginationDto;
import com.aey.papers_and_notes_api.common.error.ErrorCode;
import com.aey.papers_and_notes_api.product.domain.repositories.ProductRepository;
import com.aey.papers_and_notes_api.product.domain.services.ProductService;
import com.aey.papers_and_notes_api.product.infrastructure.persistence.models.ProductJpa;
import com.aey.papers_and_notes_api.product.infrastructure.rest.dto.ProductDto;
import io.vavr.control.Either;
import org.springframework.beans.factory.annotation.Autowired;
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
                .map(ProductJpa::toEntity)
                .map(ProductDto::fromEntity)
                .toList();
        Integer totalProducts = productRepository.countAllAvailableProducts();
        Integer lastPage = PaginationDto.calcLastPage(limit, totalProducts);
        Integer page = PaginationDto.calcPage(limit, totalProducts);
        return PaginationDto.<ProductDto>builder()
                .totalItems(totalProducts)
                .lastPage(lastPage)
                .items(products)
                .page(page)
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
