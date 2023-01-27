package com.kode19.userservice.repository;

import com.kode19.userservice.entity.AuthUserAccount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthUserAccountRepository extends JpaRepository<AuthUserAccount, Long> {
    Optional<AuthUserAccount> findByUsernameOrEmail(String userName, String email);
}