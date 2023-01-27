package com.kode19.userservice.dto.converter;

import com.kode19.userservice.dto.AuthUserAccountDTO;
import com.kode19.userservice.dto.request.AuthUserAccountRequestDTO;
import com.kode19.userservice.entity.AuthUserAccount;

import java.time.LocalDateTime;

public class AuthUserAccountConverter {
    public static AuthUserAccount convertRequestDTOtoEntity(AuthUserAccountRequestDTO dto) {
        return AuthUserAccount.builder()
                .createdDate(LocalDateTime.now())
                .email(dto.getEmail())
                .fullName(dto.getFullName())
                .password(dto.getPassword())
                .username(dto.getUsername())
                .build();
    }

    public static AuthUserAccountDTO convertEntityToDTO(AuthUserAccount entity) {
        return AuthUserAccountDTO.builder()
                .createdDate(LocalDateTime.now())
                .fullName(entity.getFullName())
                .username(entity.getUsername())
                .email(entity.getEmail())
                .id(entity.getSecureId())
                .build();

    }
}
