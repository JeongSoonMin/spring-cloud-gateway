package com.jesomi.springcloudgateway.exception;

public class ForbiddenException extends RuntimeException {
    public ForbiddenException() {
        super("권한이 충분하지 않습니다.");
    }

    public ForbiddenException(String message) {
        super(message);
    }

}
