package com.example.assignmentapp.enumeration;

import org.springframework.http.HttpStatus;

public interface IExceptionType {
    String getMessage();
    HttpStatus getHttpStatus();
}
