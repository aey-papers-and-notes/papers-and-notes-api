package com.aey.papers_and_notes_api.product.infrastructure.persistence.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "prod05_tags")
public class TagJpa {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "tag_id", unique = true, nullable = false)
    private Integer tagId;

    @Column(name = "tag_tx_name")
    private String name;

    @Column(name = "tag_st_is_active")
    private Boolean isActive;

    @Column(name = "tag_dt_created_at")
    private Date createdAt;

    @Column(name = "tag_dt_updated_at")
    private Date updatedAt;
}
