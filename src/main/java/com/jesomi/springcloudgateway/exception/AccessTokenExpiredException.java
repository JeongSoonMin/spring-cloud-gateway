package com.jesomi.springcloudgateway.exception;

import lombok.Getter;

@Getter
public class AccessTokenExpiredException extends RuntimeException {
    private final String errorCode = "COMMON_ACCESS_TOKEN_EXPIRE";

    public AccessTokenExpiredException() {
        super("액세스 토근이 만료되었습니다.");
    }

    public AccessTokenExpiredException(String message) {
        super(message);
    }

}
