package com.kode19.userservice.service;

import com.kode19.userservice.dto.AuthRoleDTO;
import com.kode19.userservice.dto.request.AuthRoleRequestDTO;
import com.kode19.userservice.dto.response.DataProcessSuccessResponseDTO;
import com.kode19.userservice.dto.response.PagingResponseDTO;

public interface AuthRoleService {

    DataProcessSuccessResponseDTO<AuthRoleDTO> createRole(AuthRoleRequestDTO authRoleRequestDTO);

    DataProcessSuccessResponseDTO<AuthRoleDTO> updateRole(String secureId, AuthRoleRequestDTO authRoleRequestDTO);

    PagingResponseDTO<AuthRoleDTO> getAllRoles(Integer page,
                                  Integer limit,
                                  String sortBy,
                                  String direction,
                                  String path);

    void deleteRole(String secureId);
}
