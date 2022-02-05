package com.example.assignmentapp.dto;


public class CourseFormUpdateDto {

    private int idCourse;
    private String name;
    private String description;

    public int getIdcourse() {
        return idCourse;
    }

    public void setIdcourse(int idCourse) {
        this.idCourse = idCourse;
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
}
