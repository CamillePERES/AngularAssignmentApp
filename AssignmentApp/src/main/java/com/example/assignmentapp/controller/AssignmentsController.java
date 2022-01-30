package com.example.assignmentapp.controller;


import com.example.assignmentapp.dto.AssignmentDto;
import com.example.assignmentapp.model.AssignmentEntity;
import com.example.assignmentapp.dto.AssignmentFormCreateDto;
import com.example.assignmentapp.service.AssignmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/assignments")
public class AssignmentsController extends BaseController{

    @Autowired
    AssignmentService assignmentService;

    @GetMapping()
    //@RolesAllowed({"TEACHER", "STUDENT"})
    @PreAuthorize("hasAuthority('TEACHER') and hasAuthority('STUDENT')")
    public ResponseEntity<List<AssignmentDto>> getAllAssignments(){
        return tryHandle(() -> {
            List<AssignmentDto> listAss = assignmentService.getAllAssignment()
                    .stream()
                    .map(assi -> new AssignmentDto(assi))
                    .collect(Collectors.toList());
            return new ResponseEntity<>(listAss, HttpStatus.OK);
        });
    }

    @GetMapping("/{id}")
    //@RolesAllowed({"TEACHER", "STUDENT"})
    @PreAuthorize("hasAuthority('TEACHER') and hasAuthority('STUDENT')")
    public ResponseEntity<AssignmentDto> getAssignment(@PathVariable("id") int id){
        return tryHandle(() -> {
                AssignmentEntity assignment = assignmentService.getAssigmentById(id);
                return new ResponseEntity<>(new AssignmentDto(assignment), HttpStatus.OK);
        });
    }

    @PostMapping()
    //@RolesAllowed("TEACHER")
    @PreAuthorize("hasAuthority('TEACHER')")
    public ResponseEntity<AssignmentDto> createAssignment(@RequestBody AssignmentFormCreateDto assignmentFormCreate){
        return tryHandle(() -> {
            AssignmentEntity assignmentCreated = assignmentService.createAssignment(assignmentFormCreate);
            return new ResponseEntity<>(new AssignmentDto(assignmentCreated), HttpStatus.OK);
        });
    }


    @DeleteMapping("/{id}")
    //@RolesAllowed("TEACHER")
    @PreAuthorize("hasAuthority('TEACHER')")
    public void deleteAssignment(@PathVariable("id") int id){
        assignmentService.deleteAssignmentById(id);
    }

}
