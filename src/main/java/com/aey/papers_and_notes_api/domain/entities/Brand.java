package com.aey.papers_and_notes_api.domain.entities;

import lombok.*;

import java.util.Date;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Brand {
    private Integer brandId;
    private String name;
    private Boolean isActive;
    private Date createdAt;
    private Date updatedAt;
}
