package com.example.assignmentapp.repositories;

import com.example.assignmentapp.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface IUserRepository extends JpaRepository<UserEntity, Integer> {

    @Query(nativeQuery = true, value = "SELECT * FROM user u  WHERE u.login = :login LIMIT 1")
    UserEntity getUserByLogin(@Param("login") String login);

}
