package com.aey.papers_and_notes_api.common.response;

import org.springframework.http.ResponseEntity;

public class ResponseCodeMapper {
    public static <T> ResponseEntity<ResponseCodeDto<T>> toResponse(ResponseCode responseCode, T data) {
        ResponseCodeDto<T> response = ResponseCodeDto
                .<T>builder()
                .statusCode(responseCode.getStatusCode())
                .message(responseCode.getMessage())
                .action(responseCode.getAction())
                .data(data)
                .build();
        return ResponseEntity
                .status(responseCode.getStatusCode())
                .body(response);
    }
}
