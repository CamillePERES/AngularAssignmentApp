package com.example.assignmentapp.exceptions;

import com.example.assignmentapp.enumeration.CourseExceptionType;

public class CourseException extends BusinessException {

    public CourseException(CourseExceptionType type){
        super(type);
    }
}
