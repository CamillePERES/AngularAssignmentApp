package com.example.assignmentapp.model;

import org.springframework.security.core.userdetails.User;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "user", schema = "assignment_db")
public class UserEntity {
    private int iduser;
    private String name;
    private String firstname;
    private String login;
    private String password;
    private String role;
    private String picturename;
    private byte[] picturebytes;
    private String picturecontenttype;
    private Collection<CourseEntity> courses;
    private Collection<WorkEntity> works;

    public UserEntity(int iduser, String name, String firstname, String login, String password, String role) {
        this.iduser = iduser;
        this.name = name;
        this.firstname = firstname;
        this.login = login;
        this.password = password;
        this.role = role;
        this.courses = new ArrayList<>();
        this.works = new ArrayList<>();
    }

    public UserEntity(){

    }

    public UserEntity(String name, String firstname, String login, String password, String role){
        this.name = name;
        this.firstname = firstname;
        this.login = login;
        this.password = password;
        this.role = role;
    }

    @Id
    @GeneratedValue()
    @Column(name = "iduser")
    public int getIduser() {
        return iduser;
    }

    public void setIduser(int iduser) {
        this.iduser = iduser;
    }

    @Basic
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "firstname")
    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    @Basic
    @Column(name = "login")
    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    @Basic
    @Column(name = "password")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Basic
    @Column(name = "role")
    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Basic
    @Column(name = "picturename")
    public String getPicturename() {
        return picturename;
    }

    public void setPicturename(String picturename) {
        this.picturename = picturename;
    }

    @Basic
    @Column(name = "picturebytes")
    public byte[] getPicturebytes() {
        return picturebytes;
    }

    public void setPicturebytes(byte[] picturebytes) {
        this.picturebytes = picturebytes;
    }

    @Basic
    @Column(name = "picturecontenttype")
    public String getPicturecontenttype() {
        return picturecontenttype;
    }

    public void setPicturecontenttype(String picturecontenttype) {
        this.picturecontenttype = picturecontenttype;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserEntity that = (UserEntity) o;
        return iduser == that.iduser && Objects.equals(name, that.name) && Objects.equals(firstname, that.firstname) && Objects.equals(login, that.login) && Objects.equals(password, that.password) && Objects.equals(role, that.role);
    }

    @Override
    public int hashCode() {
        return Objects.hash(iduser, name, firstname, login, password, role);
    }

    @OneToMany(mappedBy = "userEntity")
    public Collection<CourseEntity> getCourses() {
        return courses;
    }

    public void setCourses(Collection<CourseEntity> courses) {
        this.courses = courses;
    }

    @OneToMany(mappedBy = "userEntity")
    public Collection<WorkEntity> getWorks() {
        return works;
    }

    public void setWorks(Collection<WorkEntity> works) {
        this.works = works;
    }

}
