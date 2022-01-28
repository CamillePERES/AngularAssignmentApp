package com.example.assignmentapp.controller;

import com.example.assignmentapp.exceptions.UserException;
import com.example.assignmentapp.model.UserEntity;
import com.example.assignmentapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UsersController extends BaseController{

    @Autowired
    UserService userService;

    @GetMapping()
    public ResponseEntity<List<UserEntity>> getAllUsers(){
            return new ResponseEntity<>(userService.getAllUser(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserEntity> getUser(@PathVariable("id") int id){
        return tryHandle(() -> {
            UserEntity user = userService.getUserById(id);
            return new ResponseEntity<>(user, HttpStatus.OK);
        });
    }

    @PostMapping()
    public ResponseEntity<UserEntity> createUser(@RequestBody UserEntity userEntity) {
        return tryHandle(() -> {
            UserEntity userCreated = userService.createUser(userEntity);
            return new ResponseEntity<>(userCreated, HttpStatus.OK);
        });
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable("id") int id){
         userService.deleteUserById(id);
    }



}
