package com.aey.papers_and_notes_api.common.response;

import lombok.Getter;

@Getter
public enum ResponseCode {
    ASSOCIATE_PRODUCT(200, "Associate product", "Product associated with a category"),;

    private final Integer statusCode;
    private final String action;
    private final String message;
    ResponseCode(Integer statusCode, String action, String message) {
        this.statusCode = statusCode;
        this.action = action;
        this.message = message;
    }
}
