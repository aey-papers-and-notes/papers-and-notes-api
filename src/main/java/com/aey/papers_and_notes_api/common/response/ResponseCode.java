package com.aey.papers_and_notes_api.common.response;

import lombok.Getter;

@Getter
public enum ResponseCode {

    //Products
    ASSOCIATE_PRODUCT(200, "Associate product", "Product associated with a category"),
    DISABLE_PRODUCT(200, "Disable product", "Product was disabled successfully"),
    ENABLE_PRODUCT(200, "Enable product", "Product was enable successfully"),
    PRODUCT_CREATED(201, "Create product", "Product was successfully created"),

    //Categories
    CATEGORY_UPDATED(200, "Update category", "Category was successfully updated"),
    DISABLE_CATEGORY(200, "Disable category", "Category was disabled successfully"),
    CATEGORY_CREATED(201, "Create category", "Category was successfully created"),
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
