package com.example.assignmentapp.repositories;

import com.example.assignmentapp.model.CourseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ICourseRepository extends JpaRepository<CourseEntity, Integer> , JpaSpecificationExecutor<CourseEntity> {

    /*@Query(nativeQuery = true, value = "SELECT * FROM course c  WHERE c.name = :name and c.iduser =: idUser LIMIT 1")
    CourseEntity getUniqueCourse(@Param("name") String name,@Param("idUser") Integer idUser);*/
}
