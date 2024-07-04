package com.jesomi.springcloudgateway.exception;

import lombok.Getter;

@Getter
public class ForbiddenException extends RuntimeException {
    private final String errorCode = "COMMON_FORBIDDEN";

    public ForbiddenException() {
        super("권한이 충분하지 않습니다.");
    }

    public ForbiddenException(String message) {
        super(message);
    }

}
