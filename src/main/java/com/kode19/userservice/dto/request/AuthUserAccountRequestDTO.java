package com.kode19.userservice.dto.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.validation.annotation.Validated;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Validated
@Builder
public class AuthUserAccountRequestDTO {

    @NotEmpty
    @Size(max = 255, min = 10)
    @Email
    private String email;
    @NotEmpty
    private String username;
    @NotEmpty
    @Size(max = 255, min = 10)
    private String fullName;
    @NotEmpty
    @Size(max = 255, min = 10)
    private String password;
    @NotEmpty
    @Size(max = 3, min = 3)
    private String branchCode;

    public void toUppercase() {
        this.email = this.email.toUpperCase();
        this.username = this.username.toUpperCase();
        this.fullName = this.fullName.toUpperCase();
        this.branchCode = this.branchCode.toUpperCase();
    }

}
