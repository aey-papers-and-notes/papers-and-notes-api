package com.aey.papers_and_notes_api.common.response;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ResponseCodeDto<T> {
    private Integer statusCode;
    private String action;
    private String message;
    private T data;

    public static <T> ResponseCodeDto<T> ok(ResponseCode responseCode, T data) {
        return ResponseCodeDto.<T>builder()
                .statusCode(responseCode.getStatusCode())
                .action(responseCode.getAction())
                .message(responseCode.getMessage())
                .data(data)
                .build();
    }
}
