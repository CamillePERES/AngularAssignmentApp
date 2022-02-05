package com.example.assignmentapp.enumeration;

import org.springframework.http.HttpStatus;

public enum UserExceptionType  implements IExceptionType {
    NOT_FOUND("User not found", HttpStatus.BAD_REQUEST)
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
