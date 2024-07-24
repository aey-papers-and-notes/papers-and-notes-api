package com.aey.papers_and_notes_api.product.application;

import com.aey.papers_and_notes_api.common.error.ErrorCode;
import com.aey.papers_and_notes_api.product.domain.entities.ProductImage;
import com.aey.papers_and_notes_api.product.domain.repositories.ProductImageRepository;
import com.aey.papers_and_notes_api.product.domain.services.ProductImageService;
import com.aey.papers_and_notes_api.product.infrastructure.rest.dtos.UploadProductImageDto;
import io.vavr.control.Either;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProductImageUseCase implements ProductImageService {

    private final ProductImageRepository productImageRepository;

    public ProductImageUseCase(ProductImageRepository productImageRepository) {
        this.productImageRepository = productImageRepository;
    }

    @Override
    public List<ProductImage> getAllProductImagesByProductId(UUID productId) {
        return productImageRepository.findAllProductImagesByProductId(productId);
    }

    @Override
    public Either<ErrorCode, ProductImage> getProductImageById(Integer imageId) {
        Optional<ProductImage> productImage = productImageRepository.findProductImageById(imageId);
        if (productImage.isEmpty()) {
            return Either.left(ErrorCode.PRODUCT_IMAGE_NOT_FOUND);
        }
        return productImage
                .<Either<ErrorCode, ProductImage>>map(Either::right)
                .orElseGet(() -> Either.left(ErrorCode.ERROR));
    }

    @Override
    @Transactional
    public Either<ErrorCode, ProductImage> saveProductImage(ProductImage productImage) {
        return productImageRepository.saveProductImage(productImage)
                .<Either<ErrorCode, ProductImage>>map(Either::right)
                .orElseGet(() -> Either.left(ErrorCode.ERROR_TO_CREATE));
    }

    @Override
    @Transactional
    public Either<ErrorCode, ProductImage> uploadProductImage(UUID productId, UploadProductImageDto uploadProductImageDto) {
        ProductImage productImage = ProductImage.builder()
                .url(uploadProductImageDto.getUrl())
                .description(uploadProductImageDto.getDescription())
                .productId(productId)
                .build();
        return productImageRepository.saveProductImage(productImage)
                .<Either<ErrorCode, ProductImage>>map(Either::right)
                .orElseGet(() -> Either.left(ErrorCode.ERROR_TO_CREATE));
    }

    @Override
    @Transactional
    public Either<ErrorCode, ProductImage> deleteProductImage(Integer imageId) {
        Either<ErrorCode, ProductImage> productImage = getProductImageById(imageId);
        if (productImage.isLeft()) {
            return Either.left(productImage.getLeft());
        }
        productImageRepository.deleteProductImage(productImage.get().getImageId());
        return Either.right(productImage.get());
    }
}
