package com.example.assignmentapp;

import com.example.assignmentapp.dto.UserFormCreateDto;
import com.example.assignmentapp.model.UserEntity;
import com.example.assignmentapp.model.UserRoleEntity;
import com.example.assignmentapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.multipart.MultipartFile;

@SpringBootApplication
public class AssignmentAppApplication implements CommandLineRunner {

    @Autowired
    UserService userService;

    public static void main(String[] args) {
        SpringApplication.run(AssignmentAppApplication.class, args);
    }


    @Override
    public void run(String... params) throws Exception {

        UserFormCreateDto entity = new UserFormCreateDto();
        entity.setFirstname("Camille");
        entity.setName("Peres");
        entity.setLogin("cperes");
        entity.setPassword("cperes");
        entity.setRole(UserRoleEntity.TEACHER.name());

        this.createUser(entity);

        UserFormCreateDto entity2 = new UserFormCreateDto();
        entity2.setFirstname("Laurent");
        entity2.setName("LeP");
        entity2.setLogin("llp");
        entity2.setPassword("llp");
        entity2.setRole(UserRoleEntity.STUDENT.name());

        this.createUser(entity2);
    }

    private void createUser(UserFormCreateDto entity) {
        try {
            userService.createUser(entity);
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
}
