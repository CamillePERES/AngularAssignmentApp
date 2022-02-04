package com.example.assignmentapp.exceptions;

import org.springframework.http.HttpStatus;

public class CourseException extends BusinessException {

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.BAD_REQUEST;
    }
}
