package com.kode19.userservice.dto.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class PagingResponseDTO<T> {

    List<T> data;
    LocalDateTime timestamp;
    String path;
    private Integer totalPages;
    private Integer currentPage;
    private Long totalElements;
    Integer statusCode;

}
