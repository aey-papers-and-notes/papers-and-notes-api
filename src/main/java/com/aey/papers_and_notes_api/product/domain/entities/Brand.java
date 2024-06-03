package com.aey.papers_and_notes_api.product.domain.entities;

import lombok.*;

import java.util.Date;
import java.util.UUID;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Brand {
    private UUID brandId;
    private String name;
    private Boolean isActive;
    private Date createdAt;
    private Date updatedAt;
}
