package com.example.assignmentapp.controller;

import com.example.assignmentapp.dto.WorkDto;
import com.example.assignmentapp.dto.WorkFormCreateDto;
import com.example.assignmentapp.dto.WorkFormEvaluationDto;
import com.example.assignmentapp.model.WorkEntity;
import com.example.assignmentapp.service.WorkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/works")
public class WorksController extends BaseController {

    @Autowired
    WorkService workService;

    @GetMapping()
    //@RolesAllowed({"TEACHER", "STUDENT"})
    //@PreAuthorize("hasAuthority('TEACHER') and hasAuthority('STUDENT')")
    public ResponseEntity<List<WorkDto>> getAllWorks() {
        return tryHandle(() -> {
            List<WorkDto> listWk = workService.getAllWork()
                    .stream()
                    .map(wk -> new WorkDto(wk))
                    .collect(Collectors.toList());
            return new ResponseEntity<>(listWk, HttpStatus.OK);
        });
    }

    @GetMapping("/{id}")
    //@RolesAllowed({"TEACHER", "STUDENT"})
    @PreAuthorize("hasAuthority('TEACHER') and hasAuthority('STUDENT')")
    public ResponseEntity<WorkDto> getWork(@PathVariable("id") int id) {
        return tryHandle(() -> {
            WorkEntity work = workService.getWorkById(id);
            return new ResponseEntity<>(new WorkDto(work), HttpStatus.OK);
        });
    }

    @PostMapping()
    //@RolesAllowed("STUDENT")
    @PreAuthorize("hasAuthority('STUDENT')")
    public ResponseEntity<WorkDto> createWork(@RequestBody WorkFormCreateDto workFormCreateDto) {
        return tryHandle(() -> {
            WorkEntity workCreated = workService.createWork(workFormCreateDto);
            return new ResponseEntity<>(new WorkDto(workCreated), HttpStatus.OK);
        });
    }

    @PutMapping()
    //@RolesAllowed("TEACHER")
    @PreAuthorize("hasAuthority('TEACHER')")
    public ResponseEntity<WorkDto> updateWorkForEvaluation(@RequestBody WorkFormEvaluationDto workFormEvaluation){
        return tryHandle(() -> {
            WorkEntity workEvaluated = workService.updateWorkForEvaluation(workFormEvaluation);
            return new ResponseEntity<>(new WorkDto(workEvaluated), HttpStatus.OK);
        });
    }

    @DeleteMapping("/{id}")
    //@RolesAllowed("STUDENT")
    @PreAuthorize("hasAuthority('STUDENT')")
    public void deleteWork(@PathVariable("id") int id) {
        workService.deleteWorkById(id);
    }
}
