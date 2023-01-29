package com.kode19.userservice.controller;

import com.kode19.userservice.dto.AuthRoleDTO;
import com.kode19.userservice.dto.request.AuthRoleRequestDTO;
import com.kode19.userservice.dto.response.DataProcessSuccessResponseDTO;
import com.kode19.userservice.dto.response.PagingResponseDTO;
import com.kode19.userservice.service.AuthRoleService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;


@RestController
@RequestMapping("api/v1/auth/role")
@Slf4j
public class AuthRoleController {
    private final AuthRoleService service;

    public AuthRoleController(AuthRoleService service) {
        this.service = service;
    }

    @GetMapping()
    public ResponseEntity<PagingResponseDTO<AuthRoleDTO>> getAllRoles(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer limit,
            @RequestParam(defaultValue = "roleName") String sortBy,
            @RequestParam(defaultValue = "asc") String direction
    ) {

        return ResponseEntity.ok(service.getAllRoles(page, limit, sortBy, direction, ServletUriComponentsBuilder.fromCurrentRequest().build().toUri().getPath()));
    }

    @PostMapping()
    public ResponseEntity<DataProcessSuccessResponseDTO<AuthRoleDTO>> registerRole(@RequestBody @Valid AuthRoleRequestDTO authRoleRequestDTO) {
        return ResponseEntity
                .created(ServletUriComponentsBuilder.fromCurrentRequest().buildAndExpand().toUri())
                .body(service.createRole(authRoleRequestDTO));
    }

    @PutMapping("{secureId}")
    public ResponseEntity<DataProcessSuccessResponseDTO<AuthRoleDTO>> updateRole(
            @PathVariable String secureId,
            @RequestBody @Valid AuthRoleRequestDTO authRoleRequestDTO
    ) {
        return ResponseEntity.ok(service.updateRole(secureId, authRoleRequestDTO));
    }

    @DeleteMapping("{secureId}")
    public ResponseEntity<Void> deleteRole(
            @PathVariable String secureId
    ) {
        service.deleteRole(secureId);
        return ResponseEntity.ok().build();
    }
}
