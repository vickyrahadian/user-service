package com.kode19.userservice.controller;

import com.kode19.userservice.dto.AuthUserAccountDTO;
import com.kode19.userservice.dto.request.AuthUserAccountRequestDTO;
import com.kode19.userservice.dto.response.DataProcessSuccessResponseDTO;
import com.kode19.userservice.service.AuthUserAccountService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;


@RestController
@RequestMapping("api/v1/auth/user")
@Slf4j
public class AuthUserAccountController {
    private final AuthUserAccountService service;

    public AuthUserAccountController(AuthUserAccountService service) {
        this.service = service;
    }

    @PostMapping()
    public ResponseEntity<DataProcessSuccessResponseDTO<AuthUserAccountDTO>> registerUserAccount(@RequestBody @Valid AuthUserAccountRequestDTO dto) {
        return ResponseEntity
                .created(ServletUriComponentsBuilder.fromCurrentRequest().buildAndExpand().toUri())
                .body(service.createUser(dto));
    }


}
