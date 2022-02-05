package com.example.assignmentapp.controller;

import com.example.assignmentapp.dto.ApiErrorResponse;
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
            BusinessException ex = (BusinessException) e;
            ApiErrorResponse resp = new ApiErrorResponse();
            resp.setStatus(ex.type.getHttpStatus().value());
            resp.setMessage(ex.type.getMessage());
            return new ResponseEntity(resp, ex.type.getHttpStatus());
        }

        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
