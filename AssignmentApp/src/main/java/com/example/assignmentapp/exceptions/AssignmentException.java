package com.example.assignmentapp.exceptions;

import com.example.assignmentapp.enumeration.AssignmentExceptionType;

public class AssignmentException extends BusinessException{

    public AssignmentException(AssignmentExceptionType type){
        super(type);
    }
}
