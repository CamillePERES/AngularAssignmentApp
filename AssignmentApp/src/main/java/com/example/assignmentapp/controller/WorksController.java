package com.example.assignmentapp.controller;

import com.example.assignmentapp.dto.WorkDto;
import com.example.assignmentapp.dto.WorkFormCreateDto;
import com.example.assignmentapp.dto.WorkFormEvaluationDto;
import com.example.assignmentapp.dto.WorkFormUpdateDto;
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
}
