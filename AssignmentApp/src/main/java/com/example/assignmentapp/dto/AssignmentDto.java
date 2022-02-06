package com.example.assignmentapp.dto;

import com.example.assignmentapp.model.AssignmentEntity;
import com.example.assignmentapp.model.WorkEntity;

import java.sql.Date;

public class AssignmentDto {

    private int idass;
    private String name;
    private Date date;
    private String description;
    private boolean isclosed;
    private CourseDto course;

    public AssignmentDto(AssignmentEntity entity) {
        this.idass = entity.getIdass();
        this.name = entity.getName();
        this.date = entity.getDate();
        this.description = entity.getDescription();
        this.isclosed = entity.getIsclosed();
        this.course = new CourseDto(entity.getCourseEntity());
    }


    public int getIdass() {
        return idass;
    }

    public void setIdass(int idass) {
        this.idass = idass;
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

    public boolean isIsclosed() {
        return isclosed;
    }

    public void setIsclosed(boolean isclosed) {
        this.isclosed = isclosed;
    }

    public CourseDto getCourse() {
        return course;
    }

    public void setCourse(CourseDto course) {
        this.course = course;
    }
}
