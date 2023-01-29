package com.kode19.userservice.service;

import com.kode19.userservice.dto.AuthUserAccountDTO;
import com.kode19.userservice.dto.request.AuthUserAccountRequestDTO;
import com.kode19.userservice.dto.response.DataProcessSuccessResponseDTO;
import com.kode19.userservice.dto.response.PagingResponseDTO;

public interface AuthUserAccountService {

    DataProcessSuccessResponseDTO<AuthUserAccountDTO> createUser(AuthUserAccountRequestDTO authUserAccountRequestDTO);

    PagingResponseDTO<AuthUserAccountDTO> getAllUserAccount(Integer page, Integer limit, String sortBy, String direction, String path);
}
