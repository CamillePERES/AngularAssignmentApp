package com.example.assignmentapp.repositories;

import com.example.assignmentapp.model.WorkEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface IWorkRepository extends JpaRepository<WorkEntity, Integer> {

    /*@Query(nativeQuery = true, value = "SELECT * FROM work w WHERE w.name =:name and w.idass = :idAss LIMIT 1")
    WorkEntity getUniqueNamedWorkByAssignment(@Param("name") String name, @Param("idass") Integer idAss);

    @Query(nativeQuery = true, value = "SELECT * FROM work w WHERE w.name =:name and w.idass = :idAss and w.iduser =:idUser LIMIT 1")
    WorkEntity getUniqueWorkByAssignmentAndUser(@Param("name") String name, @Param("idass") Integer idAss, @Param("iduser") Integer idUser);*/

}
