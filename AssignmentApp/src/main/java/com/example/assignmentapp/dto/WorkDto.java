package com.example.assignmentapp.dto;

import com.example.assignmentapp.model.WorkEntity;

import java.sql.Date;

public class WorkDto {

    private int idwork;
    private String name;
    private String description;
    private int grade;
    private String comment;
    private String status;
    private Date deliverydate;
    private UserDto user;
    private AssignmentDto assignment;

    public WorkDto() {
    }

    public WorkDto(WorkEntity entity){
        this.idwork = entity.getIdwork();
        this.name = entity.getName();
        this.description = entity.getDescription();
        this.grade = entity.getGrade();
        this.comment = entity.getComment();
        this.status = entity.getStatus();
        this.deliverydate = entity.getDeliverydate();
        this.user = new UserDto(entity.getUserEntity());
        this.assignment = new AssignmentDto(entity.getAssignmentEntity());
    }

    public int getIdwork() {
        return idwork;
    }

    public void setIdwork(int idwork) {
        this.idwork = idwork;
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

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getDeliverydate() {
        return deliverydate;
    }

    public void setDeliverydate(Date deliverydate) {
        this.deliverydate = deliverydate;
    }

    public UserDto getUser() {
        return user;
    }

    public void setUser(UserDto user) {
        this.user = user;
    }

    public AssignmentDto getAssignment() {
        return assignment;
    }

    public void setAssignment(AssignmentDto assignment) {
        this.assignment = assignment;
    }
}
