package com.aey.papers_and_notes_api.common.response;

import org.springframework.http.ResponseEntity;

public class ResponseCodeMapper {
    public static ResponseEntity<ResponseCodeDto> toResponse(ResponseCode responseCode) {
        ResponseCodeDto response = ResponseCodeDto
                .builder()
                .statusCode(responseCode.getStatusCode())
                .message(responseCode.getMessage())
                .action(responseCode.getAction())
                .build();
        return ResponseEntity
                .status(responseCode.getStatusCode())
                .body(response);
    }
}
