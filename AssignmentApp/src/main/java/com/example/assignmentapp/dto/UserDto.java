package com.example.assignmentapp.dto;

import com.example.assignmentapp.model.UserEntity;

public class UserDto {

    private int iduser;
    private String name;
    private String firstname;
    private String login;
    private String role;

    public UserDto() {
    }

    public UserDto(UserEntity entity){
        this.iduser = entity.getIduser();
        this.firstname = entity.getFirstname();
        this.name = entity.getName();
        this.login = entity.getLogin();
        this.role = entity.getRole();
    }

    public int getIduser() {
        return iduser;
    }

    public void setIduser(int iduser) {
        this.iduser = iduser;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
