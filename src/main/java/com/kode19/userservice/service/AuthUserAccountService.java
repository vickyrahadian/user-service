package com.kode19.userservice.service;

import com.kode19.userservice.dto.AuthUserAccountDTO;
import com.kode19.userservice.dto.request.AuthRoleRequestDTO;
import com.kode19.userservice.dto.request.AuthUserAccountRequestDTO;
import com.kode19.userservice.dto.response.DataProcessSuccessResponseDTO;
import com.kode19.userservice.dto.response.PagingResponseDTO;

public interface AuthUserAccountService {

    DataProcessSuccessResponseDTO createUser(AuthUserAccountRequestDTO authUserAccountRequestDTO);
}
