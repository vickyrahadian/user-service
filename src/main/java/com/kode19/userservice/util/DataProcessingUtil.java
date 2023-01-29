package com.kode19.userservice.util;

import com.kode19.userservice.dto.response.DataProcessSuccessResponseDTO;

import java.time.LocalDateTime;

public class DataProcessingUtil {

    private DataProcessingUtil() {
        throw new IllegalStateException("Utility class");
    }

    public static <T> DataProcessSuccessResponseDTO<T> createResultDataProcessingDTO(
            T data,
            String path,
            Integer status,
            String message) {

        DataProcessSuccessResponseDTO<T> result = new DataProcessSuccessResponseDTO<>();
        result.setData(data);
        result.setTimestamp(LocalDateTime.now());
        result.setPath(path);
        result.setStatusCode(status);
        result.setMessage(message);
        return result;
    }

}
