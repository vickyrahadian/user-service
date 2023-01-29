package com.kode19.userservice.controller;

import com.kode19.userservice.dto.AuthUserAccountDTO;
import com.kode19.userservice.dto.request.AuthUserAccountRequestDTO;
import com.kode19.userservice.dto.response.DataProcessSuccessResponseDTO;
import com.kode19.userservice.dto.response.PagingResponseDTO;
import com.kode19.userservice.service.AuthUserAccountService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;


@RestController
@RequestMapping("api/v1/auth/user")
@Slf4j
public class AuthUserAccountController {
    private final AuthUserAccountService service;

    public AuthUserAccountController(AuthUserAccountService service) {
        this.service = service;
    }

    @GetMapping()
    public ResponseEntity<PagingResponseDTO> getAllUserAccount(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer limit,
            @RequestParam(defaultValue = "username") String sortBy,
            @RequestParam(defaultValue = "asc") String direction
    ) {

        return ResponseEntity.ok(service.getAllUserAccount(page, limit, sortBy, direction, ServletUriComponentsBuilder.fromCurrentRequest().build().toUri().getPath()));
    }

    @PostMapping()
    public ResponseEntity<DataProcessSuccessResponseDTO<AuthUserAccountDTO>> registerUserAccount(@RequestBody @Valid AuthUserAccountRequestDTO dto) {
        return ResponseEntity
                .created(ServletUriComponentsBuilder.fromCurrentRequest().buildAndExpand().toUri())
                .body(service.createUser(dto));
    }


}
