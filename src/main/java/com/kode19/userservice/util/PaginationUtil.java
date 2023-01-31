package com.kode19.userservice.util;

import com.kode19.userservice.dto.response.PagingResponseDTO;
import org.springframework.data.domain.Sort;

import java.time.LocalDateTime;
import java.util.List;

public class PaginationUtil {

    private PaginationUtil() {
    }

    public static <T> PagingResponseDTO<T> createResultPageDTO(
            List<T> tList,
            Long totalElements,
            Integer currentPage,
            Integer pages,
            String path,
            Integer status) {

        PagingResponseDTO<T> result = new PagingResponseDTO<>();
        result.setData(tList);
        result.setTotalPages(pages);
        result.setTotalElements(totalElements);
        result.setCurrentPage(currentPage);
        result.setTimestamp(LocalDateTime.now());
        result.setPath(path);
        result.setStatusCode(status);
        return result;
    }

    public static Sort.Direction getSortBy(String sortBy) {
        if (sortBy.equalsIgnoreCase("asc")) {
            return Sort.Direction.ASC;
        } else {
            return Sort.Direction.DESC;
        }
    }

}
