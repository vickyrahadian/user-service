package com.kode19.userservice.service;

import com.kode19.userservice.dto.AuthBranchDTO;
import com.kode19.userservice.dto.request.AuthBranchRequestDTO;
import com.kode19.userservice.dto.response.DataProcessSuccessResponseDTO;
import com.kode19.userservice.entity.AuthBranch;

import java.util.Optional;

public interface AuthBranchService {

    DataProcessSuccessResponseDTO<AuthBranchDTO> createRole(AuthBranchRequestDTO authBranchRequestDTO);

    Optional<AuthBranch> findByCodeOrAbbrName(Long code, String abbrName);
}
