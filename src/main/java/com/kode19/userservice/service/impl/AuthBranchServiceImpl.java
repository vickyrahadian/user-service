package com.kode19.userservice.service.impl;

import com.kode19.userservice.dto.AuthBranchDTO;
import com.kode19.userservice.dto.converter.AuthBranchConverter;
import com.kode19.userservice.dto.request.AuthBranchRequestDTO;
import com.kode19.userservice.dto.response.DataProcessSuccessResponseDTO;
import com.kode19.userservice.entity.AuthBranch;
import com.kode19.userservice.exception.customexception.ErrorDataProcessingException;
import com.kode19.userservice.repository.AuthBranchRepository;
import com.kode19.userservice.service.AuthBranchService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class AuthBranchServiceImpl implements AuthBranchService {


    @Value("${message.added.successfully}")
    public String MESSAGES_ADDED_SUCCESSFULLY;

    @Value("${message.exception.duplicate.keys}")
    public String EXCEPTION_DUPLICATE_KEYS;

    private final AuthBranchRepository authBranchRepository;

    public AuthBranchServiceImpl(AuthBranchRepository authBranchRepository) {
        this.authBranchRepository = authBranchRepository;
    }

    @Override
    public DataProcessSuccessResponseDTO createRole(AuthBranchRequestDTO authBranchRequestDTO) {
        authBranchRequestDTO.toUppercase();

        if (authBranchRepository.findByBranchCodeOrBranchAbbreviation(authBranchRequestDTO.getBranchCode(), authBranchRequestDTO.getBranchAbbreviation()).isPresent()) {
            throw new ErrorDataProcessingException(String.format(EXCEPTION_DUPLICATE_KEYS, authBranchRequestDTO.getBranchCode() + "/" + authBranchRequestDTO.getBranchAbbreviation()));
        }

        AuthBranch authBranch = AuthBranchConverter.convertRequestDTOtoEntity(authBranchRequestDTO);
        AuthBranchDTO authRoleDTO = AuthBranchConverter.convertEntityToDTO(authBranchRepository.save(authBranch));

        return DataProcessSuccessResponseDTO.builder()
                .path(ServletUriComponentsBuilder.fromCurrentRequest().build().toUri().getPath())
                .message(String.format(MESSAGES_ADDED_SUCCESSFULLY, "BRANCH"))
                .timestamp(LocalDateTime.now())
                .data(authRoleDTO)
                .statusCode(HttpStatus.CREATED.value())
                .build();
    }

    @Override
    public Optional<AuthBranch> findByCodeOrAbbrName(Long code, String abbrName) {
        return authBranchRepository.findByBranchCodeOrBranchAbbreviation(code, abbrName);
    }
}
