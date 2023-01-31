package com.kode19.userservice.dto.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.validation.annotation.Validated;

@Getter
@Setter
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Validated
public class AuthRoleRequestDTO {

    @NotEmpty
    @Size(max = 255, min = 10)
    private String roleName;

    public void toUppercase(){
        this.roleName = this.roleName.toUpperCase();
    }

}
