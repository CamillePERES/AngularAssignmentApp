package com.example.assignmentapp.dto;

import com.example.assignmentapp.model.AssignmentEntity;
import com.example.assignmentapp.model.CourseEntity;

import java.util.Collection;
import java.util.stream.Collectors;

public class CourseDto {

    private int idcourse;
    private String name;
    private String description;
    private UserDto user;

    public CourseDto(){

    }

    public CourseDto(CourseEntity entity) {
        this.idcourse = entity.getIdcourse();
        this.name = entity.getName();
        this.description = entity.getDescription();
        this.user = new UserDto(entity.getUserEntity());
    }

    public int getIdcourse() {
        return idcourse;
    }

    public void setIdcourse(int idcourse) {
        this.idcourse = idcourse;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public UserDto getUser() {
        return user;
    }

    public void setUser(UserDto user) {
        this.user = user;
    }
}
