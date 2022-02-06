package com.example.assignmentapp.dto;

import java.sql.Date;

public class WorkFormCreateDto {

    private String name;
    private String description;
    private int idAss;

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

    public int getIdAss() {
        return idAss;
    }

    public void setIdAss(int idAss) {
        this.idAss = idAss;
    }
}
