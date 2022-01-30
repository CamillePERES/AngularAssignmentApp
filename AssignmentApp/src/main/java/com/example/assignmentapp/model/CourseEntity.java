package com.example.assignmentapp.model;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "course", schema = "assignment_db")
public class CourseEntity {
    private int idcourse;
    private String name;
    private String description;
    private Collection<AssignmentEntity> assignments;
    private UserEntity userEntity;

    public CourseEntity(String name, UserEntity userEntity) {
        this.name = name;
        this.userEntity = userEntity;
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
