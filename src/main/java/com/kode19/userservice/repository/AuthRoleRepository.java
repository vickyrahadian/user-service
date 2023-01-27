package com.kode19.userservice.repository;

import com.kode19.userservice.entity.AuthRole;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthRoleRepository extends JpaRepository<AuthRole, Long> {

    Optional<AuthRole> findByRoleName(String roleName);

    Optional<AuthRole> findBySecureId(String secureId);

    @Override
    Page<AuthRole> findAll(Pageable pageable);

}
