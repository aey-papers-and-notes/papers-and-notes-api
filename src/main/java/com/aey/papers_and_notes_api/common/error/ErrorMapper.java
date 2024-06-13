package com.aey.papers_and_notes_api.common.error;

import org.springframework.http.ResponseEntity;

public class ErrorMapper {

    public static ResponseEntity toResponse(ErrorCode errorCode) {
        ErrorDto error = ErrorDto
                .builder()
                .statusCode(errorCode.getStatusCode())
                .message(errorCode.getMessage())
                .errorCode(errorCode.getErrorCode())
                .build();
        return ResponseEntity
                .status(errorCode.getStatusCode())
                .body(error);
    }
}
