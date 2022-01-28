package com.example.assignmentapp.exceptions;

import org.springframework.http.HttpStatus;

public abstract class BusinessException extends Exception {

    public abstract HttpStatus getHttpStatus();

}
