package com.example.assignmentapp.enumeration;

import org.springframework.http.HttpStatus;

public enum WorkExceptionType  implements IExceptionType {


    ;

    private String message;
    private HttpStatus httpStatus;

    WorkExceptionType(String message, HttpStatus httpStatus) {
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
