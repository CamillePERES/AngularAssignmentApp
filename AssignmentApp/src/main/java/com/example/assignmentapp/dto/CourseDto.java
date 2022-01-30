package com.example.assignmentapp.dto;

import com.example.assignmentapp.model.AssignmentEntity;
import com.example.assignmentapp.model.CourseEntity;

import java.util.Collection;

public class CourseDto {

    private int idcourse;
    private String name;
    private String description;
    private Collection<AssignmentEntity> assignments;
    private int idUser;

    public CourseDto(){

    }

    public CourseDto(CourseEntity entity) {
        this.idcourse = entity.getIdcourse();
        this.name = entity.getName();
        this.description = entity.getDescription();
        this.assignments = entity.getAssignments();
        this.idUser = entity.getUserEntity().getIduser();
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

    public Collection<AssignmentEntity> getAssignments() {
        return assignments;
    }

    public void setAssignments(Collection<AssignmentEntity> assignments) {
        this.assignments = assignments;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }
}
