package com.example.assignmentapp.controller;

import com.example.assignmentapp.dto.CourseFormCreate;
import com.example.assignmentapp.model.CourseEntity;
import com.example.assignmentapp.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/courses")
public class CoursesController extends BaseController {

    @Autowired
    CourseService courseService;

    @GetMapping
    public ResponseEntity<List<CourseEntity>> getAllCourses(){
        return new ResponseEntity<>(courseService.getAllCourses(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CourseEntity> getCourse(@PathVariable("id") int id){
        return tryHandle(() -> {
            CourseEntity course = courseService.getCourseById(id);
            return new ResponseEntity<>(course, HttpStatus.OK);
        });
    }

    @PostMapping
    public ResponseEntity<CourseEntity> createCourse(@RequestBody CourseFormCreate courseFormCreate){
        return tryHandle(() -> {
            CourseEntity course = courseService.createCourse(courseFormCreate);
            return new ResponseEntity<>(course, HttpStatus.OK);
        });
    }

    @DeleteMapping("/{id}")
    public void deleteCourse(@PathVariable("id") int id){
        courseService.deleteCourseById(id);
    }
}
