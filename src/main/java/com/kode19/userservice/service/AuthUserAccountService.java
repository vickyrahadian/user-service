package com.kode19.userservice.service;

import com.kode19.userservice.dto.request.AuthUserAccountRequestDTO;
import com.kode19.userservice.dto.response.DataProcessSuccessResponseDTO;
import com.kode19.userservice.dto.response.PagingResponseDTO;

public interface AuthUserAccountService {

    DataProcessSuccessResponseDTO createUser(AuthUserAccountRequestDTO authUserAccountRequestDTO);

    PagingResponseDTO getAllUserAccount(Integer page, Integer limit, String sortBy, String direction, String path);
}
