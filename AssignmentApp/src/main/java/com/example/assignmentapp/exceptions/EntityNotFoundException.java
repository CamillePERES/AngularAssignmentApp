package com.example.assignmentapp.exceptions;

import org.springframework.http.HttpStatus;

public class EntityNotFoundException extends BusinessException {

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.NOT_FOUND;
    }
}
