package com.kode19.userservice.service.impl;

import com.kode19.userservice.dto.request.AuthRoleRequestDTO;
import com.kode19.userservice.dto.AuthRoleDTO;
import com.kode19.userservice.dto.converter.AuthRoleConverter;
import com.kode19.userservice.dto.response.DataProcessSuccessResponseDTO;
import com.kode19.userservice.dto.response.PagingResponseDTO;
import com.kode19.userservice.entity.AuthRole;
import com.kode19.userservice.exception.customexception.ErrorDataProcessingException;
import com.kode19.userservice.repository.AuthRoleRepository;
import com.kode19.userservice.service.AuthRoleService;
import com.kode19.userservice.util.PaginationUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class AuthRoleServiceImpl implements AuthRoleService {

    private final AuthRoleRepository authRoleRepository;

    @Value("${message.added.successfully}")
    public String MESSAGES_ADDED_SUCCESSFULLY;

    @Value("${message.updated.successfully}")
    public String MESSAGES_UPDATED_SUCCESSFULLY;

    @Value("${message.exception.duplicate.keys}")
    public String EXCEPTION_DUPLICATE_KEYS;

    @Value("${message.exception.data.not.found}")
    public String EXCEPTION_DATA_NOT_FOUND;


    public AuthRoleServiceImpl(AuthRoleRepository authRoleRepository) {
        this.authRoleRepository = authRoleRepository;
    }

    @Override
    public DataProcessSuccessResponseDTO createRole(AuthRoleRequestDTO authRoleRequestDTO) {
        authRoleRequestDTO.toUppercase();

        if (authRoleRepository.findByRoleName(authRoleRequestDTO.getRoleName()).isPresent()) {
            throw new ErrorDataProcessingException(String.format(EXCEPTION_DUPLICATE_KEYS, authRoleRequestDTO.getRoleName()));
        }

        AuthRole authRole = AuthRoleConverter.convertRequestDTOtoEntity(authRoleRequestDTO);
        AuthRoleDTO authRoleDTO = AuthRoleConverter.convertEntityToDTO(authRoleRepository.save(authRole));

        return DataProcessSuccessResponseDTO.builder()
                .path(ServletUriComponentsBuilder.fromCurrentRequest().build().toUri().getPath())
                .message(String.format(MESSAGES_ADDED_SUCCESSFULLY, "ROLE"))
                .timestamp(LocalDateTime.now())
                .data(authRoleDTO)
                .statusCode(HttpStatus.CREATED.value())
            .build();
    }

    @Override
    public DataProcessSuccessResponseDTO updateRole(String secureId, AuthRoleRequestDTO authRoleRequestDTO) {
        authRoleRequestDTO.toUppercase();

        Optional<AuthRole> authRole = authRoleRepository.findBySecureId(secureId);

        if (authRole.isEmpty()) {
            throw new ErrorDataProcessingException(String.format(EXCEPTION_DATA_NOT_FOUND, secureId));
        }

        if (authRoleRepository.findByRoleName(authRoleRequestDTO.getRoleName()).isPresent()) {
            throw new ErrorDataProcessingException(String.format(EXCEPTION_DUPLICATE_KEYS, authRoleRequestDTO.getRoleName()));
        }

        AuthRole updatedAuthRole = AuthRoleConverter.convertRequestDTOtoEntity(authRoleRequestDTO);
        updatedAuthRole.setId(authRole.get().getId());
        updatedAuthRole.setSecureId(authRole.get().getSecureId());
        updatedAuthRole.setModifiedDate(LocalDateTime.now());

        AuthRoleDTO authRoleDTO = AuthRoleConverter.convertEntityToDTO(authRoleRepository.save(updatedAuthRole));

        return DataProcessSuccessResponseDTO.builder()
                .path(ServletUriComponentsBuilder.fromCurrentRequest().build().toUri().getPath())
                .message(String.format(MESSAGES_UPDATED_SUCCESSFULLY, "ROLE"))
                .timestamp(LocalDateTime.now())
                .data(authRoleDTO)
                .statusCode(HttpStatus.OK.value())
                .build();
    }

    @Override
    public void deleteRole(String secureId) {
        Optional<AuthRole> authRole = authRoleRepository.findBySecureId(secureId);
        if (authRole.isEmpty()) {
            throw new ErrorDataProcessingException(String.format(EXCEPTION_DATA_NOT_FOUND, secureId));
        }

        authRole.get().setDeleted(true);
        authRole.get().setModifiedDate(LocalDateTime.now());

        authRoleRepository.save(authRole.get());

    }

    @Override
    public PagingResponseDTO getAllRoles(Integer page,
                                         Integer limit,
                                         String sortBy,
                                         String direction,
                                         String path) {

        Sort sort = Sort.by(new Sort.Order(PaginationUtil.getSortBy(direction), sortBy));
        Pageable pageable = PageRequest.of(page, limit, sort);

        Page<AuthRole> authRoles = authRoleRepository.findAll(pageable);

        List<AuthRoleDTO> dto = authRoles.stream()
                .map(AuthRoleConverter::convertEntityToDTO).toList();

        return PaginationUtil.createResultPageDTO(dto,
                authRoles.getTotalElements(),
                authRoles.getNumber(),
                authRoles.getTotalPages(),
                path,
                HttpStatus.OK.value());
    }


}
