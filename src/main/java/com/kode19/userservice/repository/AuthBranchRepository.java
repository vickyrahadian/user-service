package com.kode19.userservice.repository;

import com.kode19.userservice.entity.AuthBranch;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigInteger;
import java.util.Optional;

public interface AuthBranchRepository extends JpaRepository<AuthBranch, Long> {
    Optional<AuthBranch> findByBranchCodeOrBranchAbbreviation(Long branchCode, String branchAbbr);
}
