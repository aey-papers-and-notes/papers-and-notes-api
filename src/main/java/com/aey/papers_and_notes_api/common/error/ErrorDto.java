package com.aey.papers_and_notes_api.common.error;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ErrorDto {
    private Integer statusCode;
    private String errorCode;
    private String message;
}
