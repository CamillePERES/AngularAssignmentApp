package com.example.assignmentapp.enumeration;

import org.springframework.http.HttpStatus;

public enum WorkExceptionType  implements IExceptionType {
    NOT_FOUND("Work not found", HttpStatus.BAD_REQUEST),
    NOT_OWNER_OF_COURSE("Can not update a work where I am not the owner of the course", HttpStatus.BAD_REQUEST),
    NOT_OWNER_OF_WORK("Can not update a work where I am not the owner", HttpStatus.BAD_REQUEST),
    STUDENT_HAVE_ALREADY_WORK("Student have already create a work for this assignment", HttpStatus.BAD_REQUEST),
    ASSIGNMENT_CLOSED("Cannot create work for a assignment closed", HttpStatus.BAD_REQUEST),
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
