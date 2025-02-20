package com.lautadev.spring_security_practice.exceptions;

import org.springframework.http.HttpStatus;

public class ApiException extends RuntimeException{

    private final HttpStatus status;

    public ApiException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

    public ApiException(String message, Throwable cause, HttpStatus status) {
        super(message, cause);
        this.status = status;
    }

    public HttpStatus getStatus() {
        return status;
    }
/*
    public static ApiException invalidPin() {
        return new ApiException("Invalid PIN", HttpStatus.FORBIDDEN);
    }*/

    public static ApiException accessDenied() {
        return new ApiException("Access Denied", HttpStatus.UNAUTHORIZED);
    }

}