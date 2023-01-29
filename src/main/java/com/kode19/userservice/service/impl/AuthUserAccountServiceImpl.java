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
import com.kode19.userservice.repository.AuthUserAccountRepository;
import com.kode19.userservice.service.AuthBranchService;
import com.kode19.userservice.service.AuthUserAccountService;
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

import java.util.List;
import java.util.Optional;

@Service
public class AuthUserAccountServiceImpl implements AuthUserAccountService {

    @Value("${message.exception.duplicate.keys}")
    public String exceptionDuplicateKey;
    @Value("${message.added.successfully}")
    public String messageAddedSuccessfully;
    @Value("${message.exception.data.not.found}")
    public String exceptionDataNotFound;


    private final AuthUserAccountRepository authUserAccountRepository;
    private final AuthBranchService authBranchService;

    public AuthUserAccountServiceImpl(AuthUserAccountRepository authUserAccountRepository, AuthBranchService authBranchService) {
        this.authUserAccountRepository = authUserAccountRepository;
        this.authBranchService = authBranchService;
    }

    @Override
    public DataProcessSuccessResponseDTO<AuthUserAccountDTO> createUser(AuthUserAccountRequestDTO authUserAccountRequestDTO) {
        authUserAccountRequestDTO.toUppercase();

        if (authUserAccountRepository.findByUsernameOrEmail(authUserAccountRequestDTO.getUsername(), authUserAccountRequestDTO.getEmail()).isPresent()) {
            throw new ErrorDataProcessingException(String.format(exceptionDuplicateKey, authUserAccountRequestDTO.getUsername() + "/" + authUserAccountRequestDTO.getEmail()));
        }

        Optional<AuthBranch> branch = authBranchService.findByCodeOrAbbrName(null, authUserAccountRequestDTO.getBranchCode());

        if (branch.isEmpty()) {
            throw new ErrorDataProcessingException(String.format(exceptionDataNotFound, authUserAccountRequestDTO.getBranchCode()));
        }

        AuthUserAccount authUserAccount = AuthUserAccountConverter.convertRequestDTOtoEntity(authUserAccountRequestDTO, branch.get());
        authUserAccount.setPassword(SecurityConfig.passwordEncoder().encode(authUserAccount.getPassword()));
        AuthUserAccountDTO authUserAccountDTO = AuthUserAccountConverter.convertEntityToDTO(authUserAccountRepository.save(authUserAccount));

        return DataProcessingUtil.createResultDataProcessingDTO(
                authUserAccountDTO,
                ServletUriComponentsBuilder.fromCurrentRequest().build().toUri().getPath(),
                HttpStatus.CREATED.value(),
                String.format(messageAddedSuccessfully, "USER ACCOUNT")
        );
    }

    @Override
    public PagingResponseDTO<AuthUserAccountDTO> getAllUserAccount(Integer page, Integer limit, String sortBy, String direction, String path) {
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
