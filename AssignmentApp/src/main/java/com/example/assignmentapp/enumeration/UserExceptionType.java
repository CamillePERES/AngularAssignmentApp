package com.example.assignmentapp.enumeration;

import org.springframework.http.HttpStatus;

public enum UserExceptionType  implements IExceptionType {
    NOT_FOUND("User not found", HttpStatus.BAD_REQUEST),
    INVALID_CREDENTIAL("Invalid username/password supplied", HttpStatus.BAD_REQUEST),
    ALREADY_EXIST_CREATE("User with same name already exist", HttpStatus.BAD_REQUEST),
    TOKEN_NOT_VALID("Expired or invalid JWT token", HttpStatus.INTERNAL_SERVER_ERROR),
    ;

    private String message;
    private HttpStatus httpStatus;

    UserExceptionType(String message, HttpStatus httpStatus) {
        this.message = message;
        this.httpStatus = httpStatus;
    }

    public String getMessage() {
        return message;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

}
