package com.example.assignmentapp.dto;

import com.example.assignmentapp.model.AssignmentEntity;
import com.example.assignmentapp.model.WorkEntity;

import java.sql.Date;
import java.util.Collection;
import java.util.stream.Collectors;

public class AssignmentDto {

    private int idass;
    private String name;
    private Date date;
    private String description;
    private int courseId;
    private Collection<WorkDto> worksByIdass;

    public AssignmentDto() {

    }

    public AssignmentDto(AssignmentEntity entity) {
        this.idass = entity.getIdass();
        this.name = entity.getName();
        this.date = entity.getDate();
        this.description = entity.getDescription();
        this.courseId = entity.getCourseEntity().getIdcourse();
        this.worksByIdass = entity.getWorks().stream().map(WorkDto::new).collect(Collectors.toList());
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

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public Collection<WorkDto> getWorksByIdass() {
        return worksByIdass;
    }

    public void setWorksByIdass(Collection<WorkDto> worksByIdass) {
        this.worksByIdass = worksByIdass;
    }
}
