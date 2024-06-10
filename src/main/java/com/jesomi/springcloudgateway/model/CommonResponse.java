package com.jesomi.springcloudgateway.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommonResponse {


    public enum Result {
        SUCCESS, FAIL
    }

    public enum ErrorType {
        ERROR, WARNING
    }

    private Result result;
    private String message;
    private String errorCode;
    private ErrorType errorType;
    private int status;



    public static CommonResponse fail(String message, String errorCode) {
        return CommonResponse.builder()
                .result(Result.FAIL)
                .message(message)
                .errorCode(errorCode)
                .errorType(ErrorType.ERROR)
                .build();
    }

    public static CommonResponse fail(String message, String errorCode, ErrorType errorType) {
        return CommonResponse.builder()
                .result(Result.FAIL)
                .message(message)
                .errorCode(errorCode)
                .errorType(errorType)
                .build();
    }

}
