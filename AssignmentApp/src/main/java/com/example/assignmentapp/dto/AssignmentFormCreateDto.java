package com.example.assignmentapp.dto;

import java.sql.Date;

public class AssignmentFormCreateDto {

    private int idAss;
    private int courseId;
    private String name;
    private Date date;
    private String description;

    public int getIdAss() {
        return idAss;
    }

    public void setIdAss(int idAss) {
        this.idAss = idAss;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int course) {
        this.courseId = course;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
