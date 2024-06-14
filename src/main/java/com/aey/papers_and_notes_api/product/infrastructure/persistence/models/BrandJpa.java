package com.aey.papers_and_notes_api.product.infrastructure.persistence.models;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "prod02_brands")
public class BrandJpa implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "brand_id", unique = true, nullable = false)
    private Integer brandId;

    @Column(name = "brd_tx_name")
    private String name;

    @Column(name = "brd_st_is_active")
    private Boolean isActive;

    @Column(name = "brd_dt_created_at")
    private Date createdAt;

    @Column(name = "brd_dt_updated_at")
    private Date updatedAt;

    @OneToMany(
            mappedBy = "brand",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL
    )
    private List<ProductJpa> products;
}
