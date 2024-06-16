package com.aey.papers_and_notes_api.common.dtos;

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
}
