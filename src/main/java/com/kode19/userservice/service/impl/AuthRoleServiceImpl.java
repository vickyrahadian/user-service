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
import com.kode19.userservice.util.DataProcessingUtil;
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
    public String messageAddedSuccessfully;

    @Value("${message.updated.successfully}")
    public String messageUpdatedSuccessfully;

    @Value("${message.exception.duplicate.keys}")
    public String exceptionDuplicateKey;

    @Value("${message.exception.data.not.found}")
    public String exceptionDataNotFound;


    public AuthRoleServiceImpl(AuthRoleRepository authRoleRepository) {
        this.authRoleRepository = authRoleRepository;
    }

    @Override
    public DataProcessSuccessResponseDTO<AuthRoleDTO> createRole(AuthRoleRequestDTO authRoleRequestDTO) {
        authRoleRequestDTO.toUppercase();

        if (authRoleRepository.findByRoleName(authRoleRequestDTO.getRoleName()).isPresent()) {
            throw new ErrorDataProcessingException(String.format(exceptionDuplicateKey, authRoleRequestDTO.getRoleName()));
        }

        AuthRole authRole = AuthRoleConverter.convertRequestDTOtoEntity(authRoleRequestDTO);
        AuthRoleDTO authRoleDTO = AuthRoleConverter.convertEntityToDTO(authRoleRepository.save(authRole));


        return DataProcessingUtil.createResultDataProcessingDTO(
                authRoleDTO,
                ServletUriComponentsBuilder.fromCurrentRequest().build().toUri().getPath(),
                HttpStatus.CREATED.value(),
                String.format(messageAddedSuccessfully, "ROLE")
        );
    }

    @Override
    public DataProcessSuccessResponseDTO<AuthRoleDTO> updateRole(String secureId, AuthRoleRequestDTO authRoleRequestDTO) {
        authRoleRequestDTO.toUppercase();

        Optional<AuthRole> authRole = authRoleRepository.findBySecureId(secureId);

        if (authRole.isEmpty()) {
            throw new ErrorDataProcessingException(String.format(exceptionDataNotFound, secureId));
        }

        if (authRoleRepository.findByRoleName(authRoleRequestDTO.getRoleName()).isPresent()) {
            throw new ErrorDataProcessingException(String.format(exceptionDuplicateKey, authRoleRequestDTO.getRoleName()));
        }

        AuthRole updatedAuthRole = AuthRoleConverter.convertRequestDTOtoEntity(authRoleRequestDTO);
        updatedAuthRole.setId(authRole.get().getId());
        updatedAuthRole.setSecureId(authRole.get().getSecureId());
        updatedAuthRole.setModifiedDate(LocalDateTime.now());

        AuthRoleDTO authRoleDTO = AuthRoleConverter.convertEntityToDTO(authRoleRepository.save(updatedAuthRole));

        return DataProcessingUtil.createResultDataProcessingDTO(
                authRoleDTO,
                ServletUriComponentsBuilder.fromCurrentRequest().build().toUri().getPath(),
                HttpStatus.CREATED.value(),
                String.format(messageUpdatedSuccessfully, "ROLE")
        );
    }

    @Override
    public void deleteRole(String secureId) {
        Optional<AuthRole> authRole = authRoleRepository.findBySecureId(secureId);
        if (authRole.isEmpty()) {
            throw new ErrorDataProcessingException(String.format(exceptionDataNotFound, secureId));
        }

        authRole.get().setDeleted(true);
        authRole.get().setModifiedDate(LocalDateTime.now());

        authRoleRepository.save(authRole.get());

    }

    @Override
    public PagingResponseDTO<AuthRoleDTO> getAllRoles(Integer page,
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
