package com.example.assignmentapp.exceptions;

import com.example.assignmentapp.enumeration.IExceptionType;

public abstract class BusinessException extends Exception {

    public IExceptionType type;

    protected BusinessException(IExceptionType type){
        this.type = type;
    }
}
