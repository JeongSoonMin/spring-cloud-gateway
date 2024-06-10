package com.jesomi.springcloudgateway.exception;

public class UnAuthorizedException extends RuntimeException {
    public UnAuthorizedException() {
        super("권한이 필요한 서비스입니다.");
    }

    public UnAuthorizedException(String message) {
        super(message);
    }

}
