package com.example.assignmentapp.controller;

import com.example.assignmentapp.dto.*;
import com.example.assignmentapp.model.CourseEntity;
import com.example.assignmentapp.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/courses")
public class CoursesController extends BaseController {

    @Autowired
    CourseService courseService;

    @GetMapping()
    @PreAuthorize("hasRole('TEACHER') or hasRole('STUDENT')")
    public ResponseEntity<List<CourseDto>> getAllCourses(){
        return tryHandle(() -> {
            List<CourseDto> listCourses = courseService.getAllCourses()
                    .stream()
                    .map(cour -> new CourseDto(cour))
                    .collect(Collectors.toList());
            return new ResponseEntity<>(listCourses, HttpStatus.OK);
        });
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('TEACHER') or hasRole('STUDENT')")
    public ResponseEntity<CourseDto> getCourse(@PathVariable("id") int id){
        return tryHandle(() -> {
            CourseEntity course = courseService.getCourseById(id);
            return new ResponseEntity<>(new CourseDto(course), HttpStatus.OK);
        });
    }

    @PostMapping()
    @PreAuthorize("hasRole('TEACHER')")
    public ResponseEntity<CourseDto> createCourse(@RequestBody CourseFormCreateDto courseFormCreate){
        return tryHandle(() -> {
            CourseEntity course = courseService.createCourse(courseFormCreate);
            return new ResponseEntity<>(new CourseDto(course), HttpStatus.OK);
        });
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('TEACHER')")
    public void deleteCourse(@PathVariable("id") int id){
        courseService.deleteCourseById(id);
    }

    @PutMapping
    @PreAuthorize("hasRole('TEACHER')")
    public ResponseEntity<CourseDto> updateCourse(@RequestBody CourseFormUpdateDto courseFormUpdateDto){
        return tryHandle(() -> {
            CourseEntity courseUpdated = courseService.updateCourse(courseFormUpdateDto);
            return new ResponseEntity<>(new CourseDto(courseUpdated), HttpStatus.OK);
        });
    }

    @PostMapping(value="/search")
    public ResponseEntity<PaginationResult<CourseDto>> search(@RequestBody CourseSearchForm form) {
         return tryHandle(() -> {
            PaginationResult<CourseDto> list = courseService.getAllCoursesPagination(form);
            return new ResponseEntity<>(list, HttpStatus.OK);
        });
    }
}
