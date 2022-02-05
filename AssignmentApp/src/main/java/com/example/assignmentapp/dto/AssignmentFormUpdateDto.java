package com.example.assignmentapp.dto;


import java.sql.Date;

public class AssignmentFormUpdateDto {

    private int idAss;
    private String name;
    private String description;
    private Date date;

    public int getIdAss() {
        return idAss;
    }

    public void setIdAss(int idAss) {
        this.idAss = idAss;
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
