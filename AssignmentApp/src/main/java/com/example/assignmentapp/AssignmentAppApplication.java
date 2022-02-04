package com.example.assignmentapp;

import com.example.assignmentapp.model.UserEntity;
import com.example.assignmentapp.model.UserRoleEntity;
import com.example.assignmentapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AssignmentAppApplication implements CommandLineRunner {

    @Autowired
    UserService userService;

    public static void main(String[] args) {
        SpringApplication.run(AssignmentAppApplication.class, args);
    }


    @Override
    public void run(String... params) throws Exception {

        UserEntity entity = new UserEntity();
        entity.setFirstname("Camille");
        entity.setName("Peres");
        entity.setLogin("cperes");
        entity.setPassword("cperes");
        entity.setRole(UserRoleEntity.TEACHER.name());

        this.createUser(entity);

        UserEntity entity2 = new UserEntity();
        entity2.setFirstname("Laurent");
        entity2.setName("LeP");
        entity2.setLogin("llp");
        entity2.setPassword("llp");
        entity2.setRole(UserRoleEntity.STUDENT.name());

        this.createUser(entity2);
    }

    private void createUser(UserEntity entity) {
        try {
            userService.createUser(entity);
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
}
