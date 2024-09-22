package com.aey.papers_and_notes_api.product.application;

import com.aey.papers_and_notes_api.common.entities.Pagination;
import com.aey.papers_and_notes_api.common.error.ErrorCode;
import com.aey.papers_and_notes_api.product.domain.entities.Category;
import com.aey.papers_and_notes_api.product.domain.entities.Product;
import com.aey.papers_and_notes_api.product.domain.entities.ProductImage;
import com.aey.papers_and_notes_api.product.domain.repositories.ProductRepository;
import com.aey.papers_and_notes_api.product.domain.services.CategoryService;
import com.aey.papers_and_notes_api.product.domain.services.ProductImageService;
import com.aey.papers_and_notes_api.product.domain.services.ProductService;
import com.aey.papers_and_notes_api.product.infrastructure.rest.dtos.*;
import io.vavr.control.Either;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ProductUseCase implements ProductService {

    private final ProductRepository productRepository;
    private final ProductImageService productImageService;
    private final CategoryService categoryService;

    ProductUseCase(
            ProductRepository productRepository,
            ProductImageService productImageService,
            CategoryService categoryService
    ) {
        this.productRepository = productRepository;
        this.productImageService = productImageService;
        this.categoryService = categoryService;
    }

    @Override
    public Pagination<Product> getAllProducts(Integer limit, Integer offset) {
        limit = limit == null ? 10 : limit;
        offset = offset == null ? 0 : offset;

        List<Product> products = productRepository.findAllProducts(limit, offset)
                .stream()
                .peek(product -> {
                    List<ProductImage> productImages = productImageService.getAllProductImagesByProductId(product.getProductId());
                    product.setProductImages(productImages);
                    List<Category> categories = categoryService.getAllCategoriesByProductId(product.getProductId());
                    product.setCategories(categories);
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
        if (product.isEmpty()) {
            return Either.left(ErrorCode.PRODUCT_NOT_FOUND);
        }
        if (product.get().getIsActive().equals(Boolean.FALSE)) {
            return Either.left(ErrorCode.PRODUCT_NOT_AVAILABLE);
        }
        Product productFound = fillProduct(productId, product.get());
        return Either.right(productFound);
    }

    @Override
    @Transactional
    public Either<ErrorCode, Product> createProduct(CreateProductDto createProductDto) {
        List<Category> categories = new ArrayList<>();
        List<ProductImage> images = new ArrayList<>();
        if (!createProductDto.getCategories().isEmpty()) {
            for (CategoryDto categoryDto : createProductDto.getCategories()) {
                Either<ErrorCode, Category> category = categoryService
                        .getCategoryById(categoryDto.getCategoryId());
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
                .productImages(images)
                .categories(categories)
                .build();
        Optional<Product> newProduct = productRepository.createProduct(product);
        if (newProduct.isEmpty()) {
            return Either.left(ErrorCode.ERROR_TO_CREATE);
        }
        if (!createProductDto.getProductImages().isEmpty()) {
            for (UploadProductImageDto imageDto: createProductDto.getProductImages()) {
                ProductImage image = ProductImage.builder()
                        .url(imageDto.getUrl())
                        .description(imageDto.getDescription())
                        .productId(newProduct.get().getProductId())
                        .build();
                Either<ErrorCode, ProductImage> imageSaved = productImageService.saveProductImage(image);
                if (imageSaved.isLeft()) {
                    return Either.left(imageSaved.getLeft());
                }
                images.add(image);
            }
            newProduct.get().setProductImages(images);
            newProduct.get().setCategories(categories);
        }
        return Either.right(newProduct.get());
    }

    @Override
    @Transactional
    public Either<ErrorCode, Product> updateProduct(UUID productId, UpdateProductDto updateProductDto) {
        Either<ErrorCode, Product> productFound = getProductById(productId);
        if (productFound.isLeft()) {
            return Either.left(productFound.getLeft());
        }
        Product productTo = Product.builder()
                .productId(productFound.get().getProductId())
                .name(updateProductDto.getName() != null ? updateProductDto.getName() : productFound.get().getName())
                .description(updateProductDto.getDescription() != null ? updateProductDto.getDescription() : productFound.get().getDescription())
                .price(updateProductDto.getPrice() != null ? updateProductDto.getPrice() : productFound.get().getPrice())
                .stock(updateProductDto.getStock() != null ? updateProductDto.getStock() : productFound.get().getStock())
                .brandId(updateProductDto.getBrandId() != null ? updateProductDto.getBrandId() : productFound.get().getBrandId())
                .createdAt(productFound.get().getCreatedAt())
                .updatedAt(new Date())
                .isActive(productFound.get().getIsActive())
                .categories(categoryService.getAllCategoriesByProductId(productId))
                .build();
        return productRepository.updateProduct(productTo)
                .<Either<ErrorCode, Product>>map(p -> Either.right(fillProduct(productId, p)))
                .orElseGet(() -> Either.left(ErrorCode.INTERNAL_SERVER_ERROR));
    }

    @Override
    @Transactional
    public Either<ErrorCode, Product> disableProduct(UUID productId) {
        Either<ErrorCode, Product> product = getProductById(productId);
        if (product.isLeft()) {
            return Either.left(product.getLeft());
        }
        Product productUpdate = product.get();
        productUpdate.setIsActive(Boolean.FALSE);
        productUpdate.setUpdatedAt(new Date());
        return productRepository.updateProduct(productUpdate)
                .<Either<ErrorCode, Product>>map(p -> Either.right(fillProduct(productId, p)))
                .orElseGet(() -> Either.left(ErrorCode.INTERNAL_SERVER_ERROR));
    }

    @Override
    @Transactional
    public Either<ErrorCode, Product> enableProduct(UUID productId) {
        Either<ErrorCode, Product> product = getProductById(productId);
        if (product.isEmpty()) {
            return Either.left(product.getLeft());
        }
        Product productUpdate = product.get();
        productUpdate.setIsActive(Boolean.TRUE);
        productUpdate.setUpdatedAt(new Date());
        return productRepository.updateProduct(productUpdate)
                .<Either<ErrorCode, Product>>map(p -> Either.right(fillProduct(productId, p)))
                .orElseGet(() -> Either.left(ErrorCode.INTERNAL_SERVER_ERROR));
    }

    @Override
    public Either<ErrorCode, ProductImage> uploadProductImage(UUID productId, UploadProductImageDto uploadProductImageDto) {
        Either<ErrorCode, Product> product = getProductById(productId);
        if (product.isLeft()) {
            return Either.left(product.getLeft());
        }
        return productImageService.uploadProductImage(product.get().getProductId(), uploadProductImageDto);
    }

    @Override
    public Either<ErrorCode, ProductImage> deleteProductImage(UUID productId, Integer imageId) {
        Either<ErrorCode, Product> product = getProductById(productId);
        if (product.isLeft()) {
            return Either.left(product.getLeft());
        }
        return productImageService.deleteProductImage(imageId);
    }

    @Override
    public Either<ErrorCode, List<Category>> getAllCategoriesByProductId(UUID productId) {
        Either<ErrorCode, Product> product = getProductById(productId);
        if (product.isLeft()) {
            return Either.left(product.getLeft());
        }
        return Either.right(categoryService.getAllCategoriesByProductId(productId));
    }

    @Override
    @Transactional
    public Either<ErrorCode, Product> addCategoriesToProduct(UUID productId, ProductCategoryAssociationDto productCategoryAssociationDto) {
        Either<ErrorCode, Product> product = getProductById(productId);
        if (product.isLeft()) {
            return Either.left(product.getLeft());
        }
        List<Category> categories = new ArrayList<>();
        if (!product.get().getCategories().isEmpty()) {
            categories.addAll(product.get().getCategories());
        }
        for (CategoryDto categoryDto : productCategoryAssociationDto.getCategories()) {
            Either<ErrorCode, Category> category = categoryService.getCategoryById(categoryDto.getCategoryId());
            if (category.isLeft()) {
                return Either.left(category.getLeft());
            }
            List<Integer> ids = categories.stream().map(Category::getCategoryId).toList();
            if (ids.contains(categoryDto.getCategoryId())) {
                return Either.left(ErrorCode.CATEGORY_ALREADY_ASSOCIATED);
            } else {
                categories.add(category.get());
            }
        }
        product.get().setCategories(categories.stream().toList());
        return productRepository.updateProduct(product.get())
                .<Either<ErrorCode, Product>>map(p -> Either.right(fillProduct(productId, p)))
                .orElseGet(() -> Either.left(ErrorCode.ERROR));
    }

    @Override
    @Transactional
    public Either<ErrorCode, Product> removeCategoriesToProduct(UUID productId, ProductCategoryAssociationDto productCategoryAssociationDto) {
        Either<ErrorCode, Product> productEither = getProductById(productId);
        if (productEither.isLeft()) {
            return Either.left(productEither.getLeft());
        }

        Product product = productEither.get();
        Set<Integer> existingCategoryIds = product.getCategories().stream()
                .map(Category::getCategoryId)
                .collect(Collectors.toSet());

        Set<Integer> categoryIdsToRemove = productCategoryAssociationDto.getCategories().stream()
                .map(CategoryDto::getCategoryId)
                .collect(Collectors.toSet());

        boolean removeSome = existingCategoryIds.removeAll(categoryIdsToRemove);
        if(!removeSome) {
            return Either.left(ErrorCode.CATEGORY_NOT_ASSOCIATED);
        }

        List<Category> updatedCategories = existingCategoryIds.stream()
                .map(categoryService::getCategoryById)
                .filter(Either::isRight)
                .map(Either::get)
                .collect(Collectors.toList());

        product.setCategories(updatedCategories);

        return productRepository.updateProduct(product)
                .<Either<ErrorCode, Product>>map(p -> Either.right(fillProduct(productId, p)))
                .orElseGet(() -> Either.left(ErrorCode.ERROR));
    }

    private Product fillProduct(UUID productId, Product product) {
        List<ProductImage> productImages = productImageService.getAllProductImagesByProductId(productId);
        List<Category> categories = categoryService.getAllCategoriesByProductId(productId);
        product.setProductImages(productImages);
        product.setCategories(categories);
        return product;
    }
}
