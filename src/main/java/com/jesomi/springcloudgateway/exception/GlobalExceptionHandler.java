package com.jesomi.springcloudgateway.exception;

import com.jesomi.springcloudgateway.model.CommonResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {


    @ResponseBody
    @ExceptionHandler({AccessTokenExpiredException.class})
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public CommonResponse accessTokenExpiredException(AccessTokenExpiredException exception) {
        return CommonResponse.fail(exception.getMessage(), "COMMON_ACCESS_TOKEN_EXPIRE");
    }

    @ResponseBody
    @ExceptionHandler({UnAuthorizedException.class})
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public CommonResponse unauthorizedException(UnAuthorizedException exception) {
        return CommonResponse.fail(exception.getMessage(), "COMMON_UNAUTHORIZED");
    }

    @ResponseBody
    @ExceptionHandler({ForbiddenException.class})
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public CommonResponse unauthorizedException(ForbiddenException exception) {
        return CommonResponse.fail(exception.getMessage(), "COMMON_FORBIDDEN");
    }

    @ResponseBody
    @ExceptionHandler({Exception.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public CommonResponse exception(Exception exception) {
        log.error(exception.getMessage(), exception);
        return CommonResponse.fail(exception.getMessage(), "INTERNAL_SERVER_ERROR");
    }
}
