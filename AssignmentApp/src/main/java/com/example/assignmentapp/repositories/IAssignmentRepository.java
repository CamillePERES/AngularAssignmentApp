package com.example.assignmentapp.repositories;

import com.example.assignmentapp.model.AssignmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface IAssignmentRepository extends JpaRepository<AssignmentEntity, Integer> {

    /*@Query(nativeQuery = true, value = "SELECT * FROM assignment a WHERE a.name = :name and a.idcourse = :idCourse LIMIT 1")
    AssignmentEntity getUniqueAssignmentByCourse(@Param("name") String name, @Param("idCourse") Integer idCourse);*/

}
