package com.example.assignmentapp.util;

import com.example.assignmentapp.model.UserEntity;

public class UserIdentity {
    private int iduser;
    private String name;
    private String firstname;
    private String login;
    private String role;

    public UserIdentity(){

    }

    public UserIdentity(UserEntity entity){
        if(entity == null){
            return;
        }
        this.iduser = entity.getIduser();
        this.name = entity.getName();
        this.firstname = entity.getFirstname();
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
