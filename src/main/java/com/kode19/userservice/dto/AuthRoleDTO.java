package com.kode19.userservice.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class AuthRoleDTO {

    private String roleName;
    private LocalDateTime createDate;
    private LocalDateTime modifiedDate;
    private String id;
    private String authorities;

}
