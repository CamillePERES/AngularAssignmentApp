package com.example.assignmentapp.exceptions;

import com.example.assignmentapp.enumeration.WorkExceptionType;

public class WorkException extends BusinessException{
    public int tot;

    public WorkException(WorkExceptionType type){
        super(type);
    }
}
