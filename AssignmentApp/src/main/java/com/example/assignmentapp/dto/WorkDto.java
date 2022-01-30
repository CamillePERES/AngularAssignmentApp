package com.example.assignmentapp.dto;

import com.example.assignmentapp.model.WorkEntity;

public class WorkDto {

    private int idwork;
    private String name;
    private String description;
    private int grade;
    private String comment;
    private String status;
    private int idUser;
    private int idAss;

    public WorkDto() {
    }

    public WorkDto(WorkEntity entity){
        this.idwork = entity.getIdwork();
        this.name = entity.getName();
        this.description = entity.getDescription();
        this.grade = entity.getGrade();
        this.comment = entity.getComment();
        this.status = entity.getStatus();
        this.idUser = entity.getUserEntity().getIduser();
        this.idAss = entity.getAssignmentEntity().getIdass();
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

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public int getIdAss() {
        return idAss;
    }

    public void setIdAss(int idAss) {
        this.idAss = idAss;
    }
}
