package com.example.assignmentapp.controller;

import com.example.assignmentapp.dto.LoginForm;
import com.example.assignmentapp.dto.LoginResult;
import com.example.assignmentapp.model.UserEntity;
import com.example.assignmentapp.service.UserService;
import com.example.assignmentapp.util.IAuthenticationFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import javax.annotation.security.RolesAllowed;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UsersController extends BaseController{

    @Autowired
    private IAuthenticationFacade authenticationFacade;

    @Autowired
    UserService userService;

    @GetMapping()
    @PreAuthorize("hasRole('TEACHER')")
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

    @PostMapping("/signup")
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

    @PostMapping("/signin")
    public ResponseEntity<LoginResult> login(@RequestBody LoginForm loginForm) {
        return tryHandle(() -> {
            LoginResult result = userService.signIn(loginForm);
            return new ResponseEntity<>(result, HttpStatus.OK);
        });
    }

    @GetMapping("/refresh")
    @RolesAllowed({"TEACHER", "STUDENT"})
    public ResponseEntity<LoginResult> refresh() {
        return tryHandle(() -> {
            LoginResult result = userService.refresh(this.authenticationFacade.getUser().getLogin());;
            return new ResponseEntity<>(result, HttpStatus.OK);
        });
    }

    @GetMapping(value = "/me")
    @RolesAllowed({"TEACHER", "STUDENT"})
    public ResponseEntity<UserEntity> whoami() {
        return tryHandle(() -> new ResponseEntity<>(this.authenticationFacade.getUser(), HttpStatus.OK));
    }

}
