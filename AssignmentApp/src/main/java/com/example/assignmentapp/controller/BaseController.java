package com.example.assignmentapp.controller;

import com.example.assignmentapp.exceptions.BusinessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

interface ControllerFunc<T>
{
    ResponseEntity<T> call() throws Exception;
}

public abstract class BaseController {

    protected <T> ResponseEntity<T> tryHandle(ControllerFunc<T> action){
        try {
            return action.call();
        } catch (Exception e){
            return handleException(e);
        }
    }

    private <T> ResponseEntity<T> handleException(Exception e) {

        if(e instanceof BusinessException){
            return new ResponseEntity<>(((BusinessException) e).getHttpStatus());
        }

        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
