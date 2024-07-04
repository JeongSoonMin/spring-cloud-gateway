package com.jesomi.springcloudgateway.exception;

import lombok.Getter;

@Getter
public class UnAuthorizedException extends RuntimeException {
    private final String errorCode = "COMMON_UNAUTHORIZED";

    public UnAuthorizedException() {
        super("권한이 필요한 서비스입니다.");
    }

    public UnAuthorizedException(String message) {
        super(message);
    }

}
