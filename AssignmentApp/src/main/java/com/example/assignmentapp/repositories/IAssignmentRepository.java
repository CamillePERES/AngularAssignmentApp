package com.example.assignmentapp.repositories;

import com.example.assignmentapp.model.AssignmentEntity;
import com.example.assignmentapp.model.CourseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.awt.print.Pageable;
import java.util.List;

public interface IAssignmentRepository extends JpaRepository<AssignmentEntity, Integer>, JpaSpecificationExecutor<AssignmentEntity> {

   @Query(nativeQuery = true, value = "SELECT * FROM assignment a WHERE a.idcourse = :idCourse")
    List<AssignmentEntity> getAllAssignmentByIdCourse(@Param("idCourse") Integer idCourse);
}
