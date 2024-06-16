package com.aey.papers_and_notes_api.product.application;

import com.aey.papers_and_notes_api.common.dtos.PaginationDto;
import com.aey.papers_and_notes_api.common.entities.Pagination;
import com.aey.papers_and_notes_api.common.error.ErrorCode;
import com.aey.papers_and_notes_api.product.domain.entities.Product;
import com.aey.papers_and_notes_api.product.domain.entities.ProductImage;
import com.aey.papers_and_notes_api.product.domain.repositories.ProductImageRepository;
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
    private final ProductImageRepository productImageRepository;

    ProductUseCase(
            ProductRepository productRepository,
            ProductImageRepository productImageRepository
    ) {
        this.productRepository = productRepository;
        this.productImageRepository = productImageRepository;
    }

    @Override
    public Pagination<Product> getAllProducts(Integer limit, Integer offset) {
        limit = limit == null ? 10 : limit;
        offset = offset == null ? 0 : offset;
        List<Product> products = productRepository.findAllProducts(limit, offset)
                .stream()
                .peek(product -> {
                    List<ProductImage> productImages = productImageRepository.findAllProductImagesByProductId(product.getProductId());
                    product.setProductImages(productImages);
                })
                .toList();
        Integer totalProducts = productRepository.countAllAvailableProducts();
        return Pagination.<Product>builder()
                .totalItems(totalProducts)
                .lastPage(Pagination.calcLastPage(limit, totalProducts))
                .page(Pagination.calcPage(limit, offset))
                .items(products)
                .build();
    }

    @Override
    public Either<ErrorCode, Product> getProductById(UUID productId) {
        Optional<Product> product = productRepository.findOneProductById(productId);
        List<ProductImage> productImages = productImageRepository.findAllProductImagesByProductId(productId);

        if (product.isEmpty()) {
            return Either.left(ErrorCode.NOT_FOUND);
        }
        if (product.get().getIsActive().equals(Boolean.FALSE)) {
            return Either.left(ErrorCode.RESOURCE_NOT_AVAILABLE);
        }

        Product productFound = product.get();
        productFound.setProductImages(productImages);
        return Either.right(productFound);
    }
}
