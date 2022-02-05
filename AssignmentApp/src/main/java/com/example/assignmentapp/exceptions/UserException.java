package com.example.assignmentapp.exceptions;

import com.example.assignmentapp.enumeration.UserExceptionType;

public class UserException extends BusinessException{
    public UserException(UserExceptionType type){
        super(type);
    }
}
