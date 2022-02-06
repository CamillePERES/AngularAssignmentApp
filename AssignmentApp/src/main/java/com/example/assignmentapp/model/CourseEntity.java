package com.example.assignmentapp.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "course", schema = "assignment_db")
public class CourseEntity {
    private int idcourse;
    private String name;
    private String description;
    private String picturename;
    private byte[] picturebytes;
    private String picturecontenttype;
    private Collection<AssignmentEntity> assignments;
    private UserEntity userEntity;

    public CourseEntity(String name, UserEntity userEntity, String description) {
        this.name = name;
        this.userEntity = userEntity;
        this.description = description;
        this.assignments = new ArrayList<>();
    }

    public CourseEntity(){

    }

    @Id
    @GeneratedValue()
    @Column(name = "idcourse")
    public int getIdcourse() {
        return idcourse;
    }

    public void setIdcourse(int idcourse) {
        this.idcourse = idcourse;
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
    @Column(name = "description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
        CourseEntity that = (CourseEntity) o;
        return idcourse == that.idcourse && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idcourse, name);
    }

    @OneToMany(mappedBy = "courseEntity")
    public Collection<AssignmentEntity> getAssignments() {
        return assignments;
    }

    public void setAssignments(Collection<AssignmentEntity> assignments) {
        this.assignments = assignments;
    }

    @ManyToOne
    @JoinColumn(name = "iduser", referencedColumnName = "iduser", nullable = false)
    public UserEntity getUserEntity() {
        return userEntity;
    }

    public void setUserEntity(UserEntity userEntity) {
        this.userEntity = userEntity;
    }
}
