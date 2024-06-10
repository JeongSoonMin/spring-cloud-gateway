package com.jesomi.springcloudgateway.exception;

public class AccessTokenExpiredException extends RuntimeException {
    public AccessTokenExpiredException() {
        super("액세스 토근이 만료되었습니다.");
    }

    public AccessTokenExpiredException(String message) {
        super(message);
    }

}
