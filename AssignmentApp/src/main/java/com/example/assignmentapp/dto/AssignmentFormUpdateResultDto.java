package com.example.assignmentapp.dto;

import com.example.assignmentapp.model.AssignmentEntity;

import java.sql.Date;

public class AssignmentFormUpdateResultDto extends AssignmentEntity {

    private String name;
    private Date date;
    private String description;

    public AssignmentFormUpdateResultDto(String name, Date date, String description) {
        this.name = name;
        this.date = date;
        this.description = description;
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
