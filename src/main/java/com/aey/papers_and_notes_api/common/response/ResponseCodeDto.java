package com.aey.papers_and_notes_api.common.response;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ResponseCodeDto {
    private Integer statusCode;
    private String action;
    private String message;
}
