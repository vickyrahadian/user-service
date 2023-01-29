package com.kode19.userservice.dto.converter;

import com.kode19.userservice.config.SecurityConfig;
import com.kode19.userservice.dto.AuthUserAccountDTO;
import com.kode19.userservice.dto.request.AuthUserAccountRequestDTO;
import com.kode19.userservice.entity.AuthBranch;
import com.kode19.userservice.entity.AuthUserAccount;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AuthUserAccountConverter {
    public static AuthUserAccount convertRequestDTOtoEntity(AuthUserAccountRequestDTO dto, AuthBranch authBranch) {
        return AuthUserAccount.builder()
                .createdDate(LocalDateTime.now())
                .email(dto.getEmail())
                .fullName(dto.getFullName())
                .password(SecurityConfig.passwordEncoder().encode(dto.getPassword()))
                .username(dto.getUsername())
                .authBranches(authBranch)
                .build();
    }

    public static AuthUserAccountDTO convertEntityToDTO(AuthUserAccount entity) {
        return AuthUserAccountDTO.builder()
                .createdDate(LocalDateTime.now())
                .fullName(entity.getFullName())
                .username(entity.getUsername())
                .email(entity.getEmail())
                .branchCode(entity.getAuthBranches().getBranchAbbreviation())
                .id(entity.getSecureId())
                .build();

    }
}
