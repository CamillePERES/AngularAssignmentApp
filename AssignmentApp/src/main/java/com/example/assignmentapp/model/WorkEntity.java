package com.example.assignmentapp.model;

import com.example.assignmentapp.dto.WorkFormCreateDto;
import com.example.assignmentapp.enumeration.EnumWorkStatus;

import javax.persistence.*;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "work", schema = "assignment_db")
public class WorkEntity {
    private int idwork;
    private String name;
    private String description;
    private int grade;
    private String comment;
    private String status;
    private Date deliverydate;
    private UserEntity userEntity;
    private AssignmentEntity assignmentEntity;

    public WorkEntity(WorkFormCreateDto workFormCreateDto, UserEntity userEntity, AssignmentEntity assignmentEntity) {
        this.name = workFormCreateDto.getName();
        this.description = workFormCreateDto.getDescription();
        this.grade = 0;
        this.comment = "";
        this.status = EnumWorkStatus.Submitted.name();
        this.deliverydate = java.sql.Date.valueOf(LocalDate.now());
        this.userEntity = userEntity;
        this.assignmentEntity = assignmentEntity;
    }

    public WorkEntity(){

    }

    @Id
    @GeneratedValue()
    @Column(name = "idwork")
    public int getIdwork() {
        return idwork;
    }

    public void setIdwork(int idwork) {
        this.idwork = idwork;
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
    @Column(name = "grade")
    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    @Basic
    @Column(name = "comment")
    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Basic
    @Column(name = "status")
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Basic
    @Column(name = "deliverydate")
    public java.sql.Date getDeliverydate() {
        return deliverydate;
    }

    public void setDeliverydate(java.sql.Date deliverydate) {
        this.deliverydate = deliverydate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WorkEntity that = (WorkEntity) o;
        return idwork == that.idwork && grade == that.grade && Objects.equals(name, that.name) && Objects.equals(comment, that.comment) && Objects.equals(status, that.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idwork, name, grade, comment, status);
    }

    @ManyToOne
    @JoinColumn(name = "iduser", referencedColumnName = "iduser", nullable = false)
    public UserEntity getUserEntity() {
        return userEntity;
    }

    public void setUserEntity(UserEntity userEntity) {
        this.userEntity = userEntity;
    }

    @ManyToOne
    @JoinColumn(name = "idass", referencedColumnName = "idass", nullable = false)
    public AssignmentEntity getAssignmentEntity() {
        return assignmentEntity;
    }

    public void setAssignmentEntity(AssignmentEntity assignmentEntity) {
        this.assignmentEntity = assignmentEntity;
    }
}
