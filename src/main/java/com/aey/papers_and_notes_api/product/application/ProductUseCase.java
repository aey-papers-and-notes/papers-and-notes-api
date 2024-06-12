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
import java.util.UUID;

@Service
public class ProductUseCase implements ProductService {

    @Autowired
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
        Integer lastPage = totalProducts % limit == 0 ? totalProducts / limit : (totalProducts / limit) + 1;
        Integer page = (offset / limit) + 1;
        return PaginationDto.<ProductDto>builder()
                .totalItems(totalProducts)
                .lastPage(lastPage)
                .items(products)
                .page(page)
                .build();
    }

    @Override
    public Either<ErrorCode, ProductDto> getProductById(UUID productId) {
        return null;
    }
}
