package com.kode19.userservice.exception;

import com.fasterxml.jackson.annotation.JsonValue;

public enum ErrorCode {

    INVALID_DATA(1),
    INTERNAL_ERROR(2),
    NETWORK_ERROR(3),
    OTHER_ERROR(4),
    DATA_NOT_FOUND(5);

    private final int code;
    ErrorCode(int code) {
        this.code = code;
    }

    @JsonValue
    public int getCode() {
        return code;
    }
}
