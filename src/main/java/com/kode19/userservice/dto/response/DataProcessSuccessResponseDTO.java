package com.kode19.userservice.dto.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class DataProcessSuccessResponseDTO<T> {
    T data;
    LocalDateTime timestamp;
    String path;
    Integer statusCode;
    String message;
}
