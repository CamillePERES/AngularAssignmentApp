package com.example.assignmentapp.controller;

import com.example.assignmentapp.dto.LoginFormDto;
import com.example.assignmentapp.dto.LoginResultDto;
import com.example.assignmentapp.dto.UserDto;
import com.example.assignmentapp.dto.UserFormCreateDto;
import com.example.assignmentapp.model.AssignmentEntity;
import com.example.assignmentapp.model.UserEntity;
import com.example.assignmentapp.service.UserService;
import com.example.assignmentapp.util.IAuthenticationFacade;
import com.example.assignmentapp.util.UserIdentity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.view.RedirectView;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
public class UsersController extends BaseController{

    @Autowired
    private IAuthenticationFacade authenticationFacade;

    @Autowired
    UserService userService;

    @GetMapping()
    //@RolesAllowed("TEACHER")
    @PreAuthorize("hasAuthority('TEACHER')")
    public ResponseEntity<List<UserDto>> getAllUsers(){
        return tryHandle(() -> {
            List<UserDto> listUs = userService.getAllUser()
                    .stream()
                    .map(us -> new UserDto(us))
                    .collect(Collectors.toList());
            return new ResponseEntity<>(listUs, HttpStatus.OK);
        });
    }

    @GetMapping("/{id}")
    //@RolesAllowed("TEACHER")
    @PreAuthorize("hasAuthority('TEACHER')")
    public ResponseEntity<UserDto> getUser(@PathVariable("id") int id){
        return tryHandle(() -> {
            UserEntity user = userService.getUserById(id);
            return new ResponseEntity<>(new UserDto(user), HttpStatus.OK);
        });
    }

    @PostMapping("/signup")
    public ResponseEntity<UserDto> createUser(@RequestBody UserFormCreateDto userFormCreateDto) {
        return tryHandle(() -> {
            UserEntity userCreated = userService.createUser(userFormCreateDto);
            return new ResponseEntity<>(new UserDto(userCreated), HttpStatus.OK);
        });
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable("id") int id){
         userService.deleteUserById(id);
    }

    @PostMapping("/signin")
    public ResponseEntity<LoginResultDto> login(@RequestBody LoginFormDto loginForm) {
        return tryHandle(() -> {
            LoginResultDto result = userService.signIn(loginForm);
            return new ResponseEntity<>(result, HttpStatus.OK);
        });
    }

    @GetMapping("/refresh")
    @PreAuthorize("hasAuthority('TEACHER') and hasAuthority('STUDENT')")
    public ResponseEntity<LoginResultDto> refresh() {
        return tryHandle(() -> {
            LoginResultDto result = userService.refresh(this.authenticationFacade.getUser().getLogin());
            return new ResponseEntity<>(result, HttpStatus.OK);
        });
    }

    @PreAuthorize("hasRole('TEACHER') or hasRole('STUDENT')")
    @GetMapping(value = "/me")
    public ResponseEntity<UserIdentity> whoAmI() {
        return tryHandle(() -> new ResponseEntity<>(this.authenticationFacade.getUser(), HttpStatus.OK));
    }

    @PostMapping(value="/savepic/{id}")
    public ResponseEntity savePicUser(@PathVariable("id") int id, @RequestParam("picture") MultipartFile multipartFile) throws IOException {
        return tryHandle(() -> {
            userService.savePicUser(id, multipartFile);
            return new ResponseEntity(HttpStatus.OK);
        });
    }

    @GetMapping(value="/pic/{id}")
    public ResponseEntity<byte[]> getPicUser(@PathVariable("id") int id){
        return tryHandle(() -> {
            return userService.getUserPic(id);
        });
    }
}
