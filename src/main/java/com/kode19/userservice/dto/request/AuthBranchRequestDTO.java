package com.kode19.userservice.dto.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
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
public class AuthBranchRequestDTO {

    @NotNull
    private Long branchCode;
    @NotEmpty
    @Size(max = 3, min = 3)
    private String branchAbbreviation;
    @NotEmpty
    @Size(max = 255, min = 10)
    private String branchName;
    @NotEmpty
    @Size(max = 3, min = 3)
    private String branchLevel;
    @NotEmpty
    @Size(max = 3, min = 3)
    private String branchRegion;

    public void toUppercase(){
        this.branchAbbreviation = this.branchAbbreviation.toUpperCase();
        this.branchName = this.branchName.toUpperCase();
        this.branchLevel = this.branchLevel.toUpperCase();
        this.branchRegion = this.branchRegion.toUpperCase();
    }

}
