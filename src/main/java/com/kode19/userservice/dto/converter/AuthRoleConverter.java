package com.kode19.userservice.dto.converter;

import com.kode19.userservice.dto.request.AuthRoleRequestDTO;
import com.kode19.userservice.dto.AuthRoleDTO;
import com.kode19.userservice.entity.AuthRole;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AuthRoleConverter {
    public static AuthRole convertRequestDTOtoEntity(AuthRoleRequestDTO dto) {
        return AuthRole.builder()
                .createdDate(LocalDateTime.now())
                .roleName(dto.getRoleName())
                .build();
    }

    public static AuthRoleDTO convertEntityToDTO(AuthRole authRole) {
        return AuthRoleDTO.builder()
                .roleName(authRole.getRoleName())
                .id(authRole.getSecureId())
                .createDate(authRole.getCreatedDate())
                .modifiedDate(authRole.getModifiedDate())
                .build();

    }
}
