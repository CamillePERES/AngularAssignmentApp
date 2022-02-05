package com.example.assignmentapp.model;

import javax.persistence.*;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "assignment", schema = "assignment_db")
public class AssignmentEntity {
    private int idass;
    private String name;
    private String description;
    private Date date;
    private CourseEntity courseEntity;
    private Collection<WorkEntity> works;

    public AssignmentEntity(){

    }

    public AssignmentEntity(String name, Date date, String description, CourseEntity courseEntity) {
        this.name = name;
        this.date = date;
        this.description = description;
        this.courseEntity = courseEntity;
        this.works = new ArrayList<>();
    }

    @Id
    @GeneratedValue()
    @Column(name = "idass")
    public int getIdass() {
        return idass;
    }

    public void setIdass(int idass) {
        this.idass = idass;
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
    @Column(name = "date")
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AssignmentEntity that = (AssignmentEntity) o;
        return idass == that.idass && Objects.equals(name, that.name) && Objects.equals(date, that.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idass, name, date);
    }

    @ManyToOne
    @JoinColumn(name = "idcourse", referencedColumnName = "idcourse", nullable = false)
    public CourseEntity getCourseEntity() {
        return courseEntity;
    }

    public void setCourseEntity(CourseEntity courseEntity) {
        this.courseEntity = courseEntity;
    }

    @OneToMany(mappedBy = "assignmentEntity")
    public Collection<WorkEntity> getWorks() {
        return works;
    }

    public void setWorks(Collection<WorkEntity> works) {
        this.works = works;
    }
}
