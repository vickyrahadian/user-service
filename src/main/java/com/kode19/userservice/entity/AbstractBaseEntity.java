package com.kode19.userservice.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Index;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Table;
import lombok.*;

import java.io.Serializable;
import java.util.UUID;


@Getter
@Setter
@NoArgsConstructor
@MappedSuperclass
@Table(indexes = {
        @Index(name = "uk_secure_id", columnList = "secure_id")
})
public abstract class AbstractBaseEntity implements Serializable {


    @Column(name = "secure_id", nullable = false, unique = true)
    private String secureId = UUID.randomUUID().toString();

    @Column(name = "deleted", nullable = false)
    private Boolean deleted = false;
}
