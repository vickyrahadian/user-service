package com.kode19.userservice.dto.converter;

import com.kode19.userservice.dto.AuthBranchDTO;
import com.kode19.userservice.dto.request.AuthBranchRequestDTO;
import com.kode19.userservice.entity.AuthBranch;

import java.time.LocalDateTime;

public class AuthBranchConverter {
    public static AuthBranch convertRequestDTOtoEntity(AuthBranchRequestDTO dto) {
        return AuthBranch.builder()
                .createdDate(LocalDateTime.now())
                .branchCode(dto.getBranchCode())
                .branchAbbreviation(dto.getBranchAbbreviation())
                .branchName(dto.getBranchName())
                .branchLevel(dto.getBranchLevel())
                .branchRegion(dto.getBranchRegion())
                .build();
    }

    public static AuthBranchDTO convertEntityToDTO(AuthBranch authRole) {
        return AuthBranchDTO.builder()
                .createdDate(authRole.getCreatedDate())
                .modifiedDate(authRole.getModifiedDate())
                .id(authRole.getSecureId())
                .branchAbbreviation(authRole.getBranchAbbreviation())
                .branchCode(authRole.getBranchCode())
                .branchLevel(authRole.getBranchLevel())
                .branchName(authRole.getBranchName())
                .branchRegion(authRole.getBranchRegion())
                .build();

    }
}
