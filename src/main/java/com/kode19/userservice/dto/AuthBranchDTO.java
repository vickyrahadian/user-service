package com.kode19.userservice.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class AuthBranchDTO {

    private String id;
    private Long branchCode;
    private String branchAbbreviation;
    private String branchName;
    private String branchLevel;
    private String branchRegion;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;

}
