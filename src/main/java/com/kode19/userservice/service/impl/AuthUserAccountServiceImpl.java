package com.kode19.userservice.service.impl;

import com.kode19.userservice.config.SecurityConfig;
import com.kode19.userservice.dto.AuthUserAccountDTO;
import com.kode19.userservice.dto.converter.AuthUserAccountConverter;
import com.kode19.userservice.dto.request.AuthUserAccountRequestDTO;
import com.kode19.userservice.dto.response.DataProcessSuccessResponseDTO;
import com.kode19.userservice.dto.response.PagingResponseDTO;
import com.kode19.userservice.entity.AuthBranch;
import com.kode19.userservice.entity.AuthUserAccount;
import com.kode19.userservice.exception.customexception.ErrorDataProcessingException;
import com.kode19.userservice.repository.AuthBranchRepository;
import com.kode19.userservice.repository.AuthUserAccountRepository;
import com.kode19.userservice.service.AuthBranchService;
import com.kode19.userservice.service.AuthUserAccountService;
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
public class AuthUserAccountServiceImpl implements AuthUserAccountService {

    @Value("${message.exception.duplicate.keys}")
    public String EXCEPTION_DUPLICATE_KEYS;
    @Value("${message.added.successfully}")
    public String MESSAGES_ADDED_SUCCESSFULLY;
    @Value("${message.exception.data.not.found}")
    public String EXCEPTION_DATA_NOT_FOUND;


    private final AuthUserAccountRepository authUserAccountRepository;
    private final AuthBranchService authBranchService;

    public AuthUserAccountServiceImpl(AuthUserAccountRepository authUserAccountRepository, AuthBranchService authBranchService) {
        this.authUserAccountRepository = authUserAccountRepository;
        this.authBranchService = authBranchService;
    }

    @Override
    public DataProcessSuccessResponseDTO createUser(AuthUserAccountRequestDTO authUserAccountRequestDTO) {
        authUserAccountRequestDTO.toUppercase();

        if (authUserAccountRepository.findByUsernameOrEmail(authUserAccountRequestDTO.getUsername(), authUserAccountRequestDTO.getEmail()).isPresent()) {
            throw new ErrorDataProcessingException(String.format(EXCEPTION_DUPLICATE_KEYS, authUserAccountRequestDTO.getUsername() + "/" + authUserAccountRequestDTO.getEmail()));
        }

        Optional<AuthBranch> branch = authBranchService.findByCodeOrAbbrName(null, authUserAccountRequestDTO.getBranchCode());

        if (branch.isEmpty()) {
            throw new ErrorDataProcessingException(String.format(EXCEPTION_DATA_NOT_FOUND, authUserAccountRequestDTO.getBranchCode()));
        }

        AuthUserAccount authUserAccount = AuthUserAccountConverter.convertRequestDTOtoEntity(authUserAccountRequestDTO, branch.get());
        authUserAccount.setPassword(SecurityConfig.passwordEncoder().encode(authUserAccount.getPassword()));

        return DataProcessSuccessResponseDTO.builder()
                .path(ServletUriComponentsBuilder.fromCurrentRequest().build().toUri().getPath())
                .message(String.format(MESSAGES_ADDED_SUCCESSFULLY, "USER ACCOUNT"))
                .timestamp(LocalDateTime.now())
                .data(AuthUserAccountConverter.convertEntityToDTO(authUserAccountRepository.save(authUserAccount)))
                .statusCode(HttpStatus.CREATED.value())
                .build();
    }

    @Override
    public PagingResponseDTO getAllUserAccount(Integer page, Integer limit, String sortBy, String direction, String path) {
        Sort sort = Sort.by(new Sort.Order(PaginationUtil.getSortBy(direction), sortBy));
        Pageable pageable = PageRequest.of(page, limit, sort);

        Page<AuthUserAccount> authUserAccounts = authUserAccountRepository.findAll(pageable);

        List<AuthUserAccountDTO> dto = authUserAccounts.stream()
                .map(AuthUserAccountConverter::convertEntityToDTO).toList();

        return PaginationUtil.createResultPageDTO(
                dto,
                authUserAccounts.getTotalElements(),
                authUserAccounts.getNumber(),
                authUserAccounts.getTotalPages(),
                path,
                HttpStatus.OK.value());
    }
}
