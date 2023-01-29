package com.kode19.userservice.service.impl;

import com.kode19.userservice.dto.AuthBranchDTO;
import com.kode19.userservice.dto.converter.AuthBranchConverter;
import com.kode19.userservice.dto.request.AuthBranchRequestDTO;
import com.kode19.userservice.dto.response.DataProcessSuccessResponseDTO;
import com.kode19.userservice.entity.AuthBranch;
import com.kode19.userservice.exception.customexception.ErrorDataProcessingException;
import com.kode19.userservice.repository.AuthBranchRepository;
import com.kode19.userservice.service.AuthBranchService;
import com.kode19.userservice.util.DataProcessingUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.Optional;

@Service
public class AuthBranchServiceImpl implements AuthBranchService {


    @Value("${message.added.successfully}")
    public String messageAddedSuccessfully;

    @Value("${message.exception.duplicate.keys}")
    public String exceptionDuplicateKeys;

    private final AuthBranchRepository authBranchRepository;

    public AuthBranchServiceImpl(AuthBranchRepository authBranchRepository) {
        this.authBranchRepository = authBranchRepository;
    }

    @Override
    public DataProcessSuccessResponseDTO<AuthBranchDTO> createRole(AuthBranchRequestDTO authBranchRequestDTO) {
        authBranchRequestDTO.toUppercase();

        if (authBranchRepository.findByBranchCodeOrBranchAbbreviation(authBranchRequestDTO.getBranchCode(), authBranchRequestDTO.getBranchAbbreviation()).isPresent()) {
            throw new ErrorDataProcessingException(String.format(exceptionDuplicateKeys, authBranchRequestDTO.getBranchCode() + "/" + authBranchRequestDTO.getBranchAbbreviation()));
        }

        AuthBranch authBranch = AuthBranchConverter.convertRequestDTOtoEntity(authBranchRequestDTO);
        AuthBranchDTO authRoleDTO = AuthBranchConverter.convertEntityToDTO(authBranchRepository.save(authBranch));

        return DataProcessingUtil.createResultDataProcessingDTO(
                authRoleDTO,
                ServletUriComponentsBuilder.fromCurrentRequest().build().toUri().getPath(),
                HttpStatus.CREATED.value(),
                String.format(messageAddedSuccessfully, "BRANCH")
        );
    }

    @Override
    public Optional<AuthBranch> findByCodeOrAbbrName(Long code, String abbrName) {
        return authBranchRepository.findByBranchCodeOrBranchAbbreviation(code, abbrName);
    }
}
