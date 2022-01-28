package com.example.assignmentapp.dto;

import java.sql.Date;

public class AssignmentFormCreate {

    private int courseId;
    private String name;
    private Date date;

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
}
