package com.aey.papers_and_notes_api.product.application;

import com.aey.papers_and_notes_api.common.entities.Pagination;
import com.aey.papers_and_notes_api.common.error.ErrorCode;
import com.aey.papers_and_notes_api.product.domain.entities.Category;
import com.aey.papers_and_notes_api.product.domain.entities.Product;
import com.aey.papers_and_notes_api.product.domain.entities.ProductImage;
import com.aey.papers_and_notes_api.product.domain.repositories.ProductImageRepository;
import com.aey.papers_and_notes_api.product.domain.repositories.ProductRepository;
import com.aey.papers_and_notes_api.product.domain.services.CategoryService;
import com.aey.papers_and_notes_api.product.domain.services.ProductService;
import com.aey.papers_and_notes_api.product.infrastructure.rest.dtos.AssociateCategoryDto;
import com.aey.papers_and_notes_api.product.infrastructure.rest.dtos.CreateProductDto;
import io.vavr.control.Either;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ProductUseCase implements ProductService {

    private final ProductRepository productRepository;
    private final ProductImageRepository productImageRepository;
    private final CategoryService categoryService;

    ProductUseCase(
            ProductRepository productRepository,
            ProductImageRepository productImageRepository,
            CategoryService categoryService
    ) {
        this.productRepository = productRepository;
        this.productImageRepository = productImageRepository;
        this.categoryService = categoryService;
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

    @Override
    @Transactional
    public Either<ErrorCode, Product> createProduct(CreateProductDto createProductDto) {
        Set<Category> categories = new HashSet<>();
        if (!createProductDto.getCategories().isEmpty()) {
            for (AssociateCategoryDto categoryDto : createProductDto.getCategories()) {
                var category = categoryService.getCategoryById(categoryDto.getCategoryId());
                if (category.isRight()) {
                    categories.add(category.get());
                } else {
                    return Either.left(category.getLeft());
                }
            }
        }
        Product product = Product.builder()
                .name(createProductDto.getName())
                .description(createProductDto.getDescription())
                .price(createProductDto.getPrice())
                .stock(createProductDto.getStock())
                .createdAt(new Date())
                .updatedAt(new Date())
                .isActive(Boolean.TRUE)
                .brandId(createProductDto.getBrandId())
                .categories(categories)
                .build();
        Optional<Product> newProduct = productRepository.createProduct(product);
        if (newProduct.isEmpty()) {
            return Either.left(ErrorCode.ERROR_TO_CREATE);
        }
        if (!createProductDto.getProductImages().isEmpty()) {
            List<ProductImage> images = new ArrayList<>();
            createProductDto.getProductImages()
                    .forEach(imageDto -> {
                        var image = ProductImage.builder()
                                .url(imageDto.getUrl())
                                .description(imageDto.getDescription())
                                .productId(newProduct.get().getProductId())
                                .build();
                        var imageSaved = productImageRepository.saveProductImage(image);
                        imageSaved.ifPresent(images::add);
                    });
            newProduct.get().setProductImages(images);
        }
        return Either.right(newProduct.get());
    }
}
