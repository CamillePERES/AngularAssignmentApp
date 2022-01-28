package com.example.assignmentapp.exceptions;

import org.springframework.http.HttpStatus;

public class AssignmentException extends BusinessException{

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.BAD_REQUEST;
    }
}
