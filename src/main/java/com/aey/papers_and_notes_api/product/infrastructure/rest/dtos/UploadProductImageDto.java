package com.aey.papers_and_notes_api.product.infrastructure.rest.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UploadProductImageDto {

    @JsonProperty
    @NotNull(message = "Image url must be not null")
    @NotEmpty(message = "Image url must not be empty")
    @NotBlank(message = "Image url must not be blank")
    private String url;

    @JsonProperty
    private String description;
}
