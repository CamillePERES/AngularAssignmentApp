package com.example.assignmentapp.repositories;

import com.example.assignmentapp.model.WorkEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IWorkRepository extends JpaRepository<WorkEntity, Integer> {

    @Query(nativeQuery = true, value = "SELECT * FROM work w WHERE w.idass = :idAss")
    List<WorkEntity> getAllWorksByIdAss(@Param("idAss") Integer idAss);

}
