package com.kode19.userservice.dto.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
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
