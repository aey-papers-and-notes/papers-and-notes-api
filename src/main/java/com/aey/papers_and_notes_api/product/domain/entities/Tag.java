package com.aey.papers_and_notes_api.product.domain.entities;

import lombok.*;

import java.util.Date;
import java.util.UUID;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Tag {
    private UUID tagId;
    private String name;
    private Boolean isActive;
    private Date createdAt;
    private Date updatedAt;
}