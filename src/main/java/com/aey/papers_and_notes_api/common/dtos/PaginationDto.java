package com.aey.papers_and_notes_api.common.dtos;

import com.aey.papers_and_notes_api.common.entities.Pagination;
import com.aey.papers_and_notes_api.product.infrastructure.rest.dtos.ProductDto;
import lombok.*;

import java.util.List;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PaginationDto<T> {
    private Integer totalItems;
    private Integer page;
    private Integer lastPage;
    private List<T> items;

    public static <T, E> PaginationDto<T> fromEntity(Pagination<E> pagination, List<T> items) {
        return PaginationDto.<T>builder()
                .totalItems(pagination.getTotalItems())
                .page(pagination.getPage())
                .lastPage(pagination.getLastPage())
                .items(items)
                .build();
    }
}
