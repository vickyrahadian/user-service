package com.kode19.userservice.controller;

import com.kode19.userservice.dto.request.AuthBranchRequestDTO;
import com.kode19.userservice.dto.request.AuthRoleRequestDTO;
import com.kode19.userservice.dto.response.DataProcessSuccessResponseDTO;
import com.kode19.userservice.service.AuthBranchService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;


@RestController
@RequestMapping("api/v1/auth/branch")
@Slf4j
public class AuthBranchController {

    private final AuthBranchService authBranchService;

    public AuthBranchController(AuthBranchService authBranchService) {
        this.authBranchService = authBranchService;
    }

    @PostMapping
    public ResponseEntity<DataProcessSuccessResponseDTO> registerRole(@RequestBody @Valid AuthBranchRequestDTO authBranchRequestDTO) {
        return ResponseEntity
                .created(ServletUriComponentsBuilder.fromCurrentRequest().buildAndExpand().toUri())
                .body(authBranchService.createRole(authBranchRequestDTO));
    }
}
