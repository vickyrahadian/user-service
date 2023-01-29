package com.kode19.userservice.service.impl;

import com.kode19.userservice.config.SecurityConfig;
import com.kode19.userservice.dto.AuthUserAccountDTO;
import com.kode19.userservice.dto.converter.AuthUserAccountConverter;
import com.kode19.userservice.dto.request.AuthUserAccountRequestDTO;
import com.kode19.userservice.dto.response.DataProcessSuccessResponseDTO;
import com.kode19.userservice.entity.AuthUserAccount;
import com.kode19.userservice.repository.AuthUserAccountRepository;
import com.kode19.userservice.exception.customexception.ErrorDataProcessingException;
import com.kode19.userservice.service.AuthUserAccountService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.time.LocalDateTime;

@Service
public class AuthUserAccountServiceImpl implements AuthUserAccountService {

    @Value("${message.exception.duplicate.keys}")
    public String EXCEPTION_DUPLICATE_KEYS;
    @Value("${message.added.successfully}")
    public String MESSAGES_ADDED_SUCCESSFULLY;


    private final AuthUserAccountRepository authUserAccountRepository;

    public AuthUserAccountServiceImpl(AuthUserAccountRepository authUserAccountRepository) {
        this.authUserAccountRepository = authUserAccountRepository;
    }

    @Override
    public DataProcessSuccessResponseDTO createUser(AuthUserAccountRequestDTO authUserAccountRequestDTO) {
        authUserAccountRequestDTO.toUppercase();

        if (authUserAccountRepository.findByUsernameOrEmail(authUserAccountRequestDTO.getUsername(), authUserAccountRequestDTO.getEmail()).isPresent()) {
            throw new ErrorDataProcessingException(String.format(EXCEPTION_DUPLICATE_KEYS, authUserAccountRequestDTO.getUsername() + "/" + authUserAccountRequestDTO.getEmail()));
        }

        AuthUserAccount authUserAccount = AuthUserAccountConverter.convertRequestDTOtoEntity(authUserAccountRequestDTO);
        authUserAccount.setPassword(SecurityConfig.passwordEncoder().encode(authUserAccount.getPassword())));

        return DataProcessSuccessResponseDTO.builder()
                .path(ServletUriComponentsBuilder.fromCurrentRequest().build().toUri().getPath())
                .message(String.format(MESSAGES_ADDED_SUCCESSFULLY, "USER ACCOUNT"))
                .timestamp(LocalDateTime.now())
                .data(AuthUserAccountConverter.convertEntityToDTO(authUserAccountRepository.save(authUserAccount)))
                .statusCode(HttpStatus.CREATED.value())
                .build();
    }
}
