package com.example.assignmentapp.exceptions;

import org.springframework.http.HttpStatus;

public class WorkException extends BusinessException{


    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.BAD_REQUEST;
    }
}
