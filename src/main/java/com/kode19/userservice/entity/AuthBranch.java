package com.kode19.userservice.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Where;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "auth_branch")
@Builder
@Where(clause = "deleted=false")
public class AuthBranch extends AbstractBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, name = "branch_code")
    private Long branchCode;
    @Column(unique = true, name = "branch_abbreviation")
    private String branchAbbreviation;
    private String branchName;
    private String branchLevel;
    private String branchRegion;

    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;

}
