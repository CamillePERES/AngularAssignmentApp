package com.example.assignmentapp.enumeration;

import org.springframework.http.HttpStatus;

public enum WorkExceptionType  implements IExceptionType {
    NOT_FOUND("Work not found", HttpStatus.BAD_REQUEST),
    NOT_OWNER_OF_COURSE("Connot update a work where is not link to an course where i am the owner", HttpStatus.BAD_REQUEST)
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
