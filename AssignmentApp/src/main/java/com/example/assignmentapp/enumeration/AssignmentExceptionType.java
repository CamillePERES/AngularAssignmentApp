package com.example.assignmentapp.enumeration;

import org.springframework.http.HttpStatus;

public enum AssignmentExceptionType implements IExceptionType {
    NOT_FOUND("Assignment not found", HttpStatus.BAD_REQUEST),
    ALREADY_EXIST_CREATE("Assignment with same name already exist", HttpStatus.BAD_REQUEST),
    ;

    private String message;
    private HttpStatus httpStatus;

    AssignmentExceptionType(String message, HttpStatus httpStatus) {
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
