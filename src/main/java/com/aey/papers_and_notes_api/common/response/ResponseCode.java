package com.aey.papers_and_notes_api.common.response;

import lombok.Getter;

@Getter
public enum ResponseCode {

    //Products
    ASSOCIATE_PRODUCT(200, "Associate product", "Product associated with a category"),
    DISABLE_PRODUCT(200, "Product disabled", "Product was disabled successfully"),
    ENABLE_PRODUCT(200, "Product enabled", "Product was enable successfully"),
    PRODUCT_CATEGORY_ASSOCIATION(200, "Category associated", "Categories have been correctly associated with the product"),
    CREATE_PRODUCT(201, "Product created", "Product was successfully created"),

    //Product image
    PRODUCT_IMAGE_UPLOADED(201, "Product image uploaded", "Product image was successfully uploaded"),
    PRODUCT_IMAGE_DELETED(200, "Product image deleted", "Product image was successfully uploaded"),

    //Categories
    UPDATE_CATEGORY(200, "Update category", "Category was successfully updated"),
    DISABLE_CATEGORY(200, "Disable category", "Category was disabled successfully"),
    CREATE_CATEGORY(201, "Create category", "Category was successfully created"),
    ;

    private final Integer statusCode;
    private final String action;
    private final String message;
    ResponseCode(Integer statusCode, String action, String message) {
        this.statusCode = statusCode;
        this.action = action;
        this.message = message;
    }
}
