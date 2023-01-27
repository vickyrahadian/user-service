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
public class AuthUserAccountDTO {

    private String id;
    private String email;
    private String username;
    private String fullName;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;

}
