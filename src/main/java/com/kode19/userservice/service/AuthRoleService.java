package com.kode19.userservice.service;

import com.kode19.userservice.dto.request.AuthRoleRequestDTO;
import com.kode19.userservice.dto.response.DataProcessSuccessResponseDTO;
import com.kode19.userservice.dto.response.PagingResponseDTO;

public interface AuthRoleService {

    DataProcessSuccessResponseDTO createRole(AuthRoleRequestDTO authRoleRequestDTO);

    DataProcessSuccessResponseDTO updateRole(String secureId, AuthRoleRequestDTO authRoleRequestDTO);

    PagingResponseDTO getAllRoles(Integer page,
                                  Integer limit,
                                  String sortBy,
                                  String direction,
                                  String path);

    void deleteRole(String secureId);
}
