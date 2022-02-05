package com.example.assignmentapp.enumeration;

import org.springframework.http.HttpStatus;

public enum CourseExceptionType  implements IExceptionType {
    NOT_FOUND("Entity not found", HttpStatus.BAD_REQUEST),
    ALREADY_EXIST_CREATE("Entity with same name already exist", HttpStatus.BAD_REQUEST),
    USER_NOT_OWNER("User not the owner of course", HttpStatus.BAD_REQUEST)
    ;

    private String message;
    private HttpStatus httpStatus;

    CourseExceptionType(String message, HttpStatus httpStatus) {
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
