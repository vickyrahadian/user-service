package com.kode19.userservice.dto.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;

import java.time.LocalDateTime;

@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DataProcessSuccessResponseDTO<T> {

    T data;
    LocalDateTime timestamp;
    String path;
    Integer statusCode;
    String message;
}
