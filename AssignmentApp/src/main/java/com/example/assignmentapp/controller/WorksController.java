package com.example.assignmentapp.controller;

import com.example.assignmentapp.dto.WorkFormCreate;
import com.example.assignmentapp.dto.WorkFormEvaluation;
import com.example.assignmentapp.model.WorkEntity;
import com.example.assignmentapp.service.WorkService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/works")
public class WorksController extends BaseController {

    @Autowired
    WorkService workService;

    @GetMapping()
    public ResponseEntity<List<WorkEntity>> getAllWorks() {
        return new ResponseEntity<>(workService.getAllWork(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<WorkEntity> getWork(@PathVariable("id") int id) {
        return tryHandle(() -> {
            WorkEntity work = workService.getWorkById(id);
            return new ResponseEntity<>(work, HttpStatus.OK);
        });
    }

    @PostMapping()
    public ResponseEntity<WorkEntity> createWork(@RequestBody WorkFormCreate workFormCreate) {
        return tryHandle(() -> {
            WorkEntity workCreated = workService.createWork(workFormCreate);
            return new ResponseEntity<>(workCreated, HttpStatus.OK);
        });
    }

    @PutMapping("/{id}")
    public ResponseEntity<WorkEntity> updateWorkForEvaluation(@RequestBody WorkFormEvaluation workFormEvaluation){
        return tryHandle(() -> {
            WorkEntity workEvaluated = workService.updateWorkForEvaluation(workFormEvaluation);
            return new ResponseEntity<>(workEvaluated, HttpStatus.OK);
        });
    }

    @DeleteMapping("/{id}")
    public void deleteWork(@PathVariable("id") int id) {
        workService.deleteWorkById(id);
    }
}
