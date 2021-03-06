package com.example.assignmentapp.controller;

import com.example.assignmentapp.dto.*;
import com.example.assignmentapp.model.WorkEntity;
import com.example.assignmentapp.service.WorkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/works")
public class WorksController extends BaseController {

    @Autowired
    WorkService workService;

    @GetMapping()
    @PreAuthorize("hasRole('TEACHER') or hasRole('STUDENT')")
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
    @PreAuthorize("hasRole('TEACHER') or hasRole('STUDENT')")
    public ResponseEntity<WorkDto> getWork(@PathVariable("id") int id) {
        return tryHandle(() -> {
            WorkEntity work = workService.getWorkById(id);
            return new ResponseEntity<>(new WorkDto(work), HttpStatus.OK);
        });
    }

    @PostMapping()
    @PreAuthorize("hasRole('STUDENT')")
    public ResponseEntity<WorkDto> createWork(@RequestBody WorkFormCreateDto workFormCreateDto) {
        return tryHandle(() -> {
            WorkEntity workCreated = workService.createWork(workFormCreateDto);
            return new ResponseEntity<>(new WorkDto(workCreated), HttpStatus.OK);
        });
    }

    @PutMapping()
    @PreAuthorize("hasRole('TEACHER')")
    public ResponseEntity<WorkDto> updateWorkForEvaluation(@RequestBody WorkFormEvaluationDto workFormEvaluation){
        return tryHandle(() -> {
            WorkEntity workEvaluated = workService.updateWorkForEvaluation(workFormEvaluation);
            return new ResponseEntity<>(new WorkDto(workEvaluated), HttpStatus.OK);
        });
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('STUDENT')")
    public void deleteWork(@PathVariable("id") int id) {
        workService.deleteWorkById(id);
    }

    @PutMapping("/update")
    @PreAuthorize("hasRole('STUDENT')")
    public ResponseEntity<WorkDto> updateWork(@RequestBody WorkFormUpdateDto workFormUpdateDto){
        return tryHandle(() -> {
            WorkEntity workUpdated = workService.updateWork(workFormUpdateDto);
            return new ResponseEntity<>(new WorkDto(workUpdated), HttpStatus.OK);
        });
    }

    @GetMapping("/assignments/{id}")
    @PreAuthorize("hasRole('TEACHER')")
    public ResponseEntity<List<WorkDto>> getWorkByIdAss(@PathVariable("id") int id){
        return tryHandle(() -> {
            List<WorkDto> workList = workService.getWorksByIdAss(id)
                    .stream()
                    .map(wk -> new WorkDto(wk))
                    .collect(Collectors.toList());
            return new ResponseEntity<>(workList, HttpStatus.OK);
        });
    }

    @GetMapping("/assignment/{id}")
    @PreAuthorize("hasRole('STUDENT')")
    public ResponseEntity<WorkDto> getWorkStudentByIdAssignment(@PathVariable("id") int id){
        return tryHandle(() -> {
            WorkDto work = workService.getWorkStudentByIdAssignment(id);
            return new ResponseEntity<>(work, HttpStatus.OK);
        });
    }

    @PostMapping(value="/search")
    public ResponseEntity<List<WorkDto>> search(@RequestBody WorkSearchFormDto form) {
        return tryHandle(() -> {
            List<WorkDto> list = workService.getAllWorkPagination(form);
            return new ResponseEntity<>(list, HttpStatus.OK);
        });
    }

    /*
    * @GetMapping("/course/{id}")
    @PreAuthorize("hasRole('TEACHER') or hasRole('STUDENT')")
    public ResponseEntity<List<AssignmentDto>> getAssignmentByIdCourse(@PathVariable("id") int id){
        return tryHandle(() -> {
            List<AssignmentDto> assigmentList = assignmentService.getAssigmnentByIdCourse(id)
                    .stream()
                    .map(assi -> new AssignmentDto(assi))
                    .collect(Collectors.toList());
            return new ResponseEntity<>(assigmentList, HttpStatus.OK);
        });
    }*/

    @GetMapping("/user/{id}")
    @PreAuthorize("hasRole('STUDENT')")
    public ResponseEntity <List<WorkDto>> getWorksByIdUser(@PathVariable("id") int id){
        return tryHandle(() -> {
            List<WorkDto> worksList = workService.getWorksByIdUser(id)
                    .stream()
                    .map(wk -> new WorkDto(wk))
                    .collect(Collectors.toList());
            return new ResponseEntity<>(worksList, HttpStatus.OK);
        });
    }
}
