package com.aey.papers_and_notes_api.product.domain.entities;

import lombok.*;

import java.util.Date;
import java.util.Set;
import java.util.UUID;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Category {
    private Integer categoryId;
    private String name;
    private String description;
    private Boolean isActive;
    private Date createdAt;
    private Date updatedAt;
}
