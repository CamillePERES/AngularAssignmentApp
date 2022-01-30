package com.example.assignmentapp.controller;

import com.example.assignmentapp.dto.CourseDto;
import com.example.assignmentapp.dto.CourseFormCreateDto;
import com.example.assignmentapp.model.CourseEntity;
import com.example.assignmentapp.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/courses")
public class CoursesController extends BaseController {

    @Autowired
    CourseService courseService;

    @GetMapping
    //@RolesAllowed({"TEACHER", "STUDENT"})
    @PreAuthorize("hasAuthority('TEACHER') and hasAuthority('STUDENT')")
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
    //@RolesAllowed({"TEACHER", "STUDENT"})
    @PreAuthorize("hasAuthority('TEACHER') and hasAuthority('STUDENT')")
    public ResponseEntity<CourseDto> getCourse(@PathVariable("id") int id){
        return tryHandle(() -> {
            CourseEntity course = courseService.getCourseById(id);
            return new ResponseEntity<>(new CourseDto(course), HttpStatus.OK);
        });
    }

    @PostMapping
    //@RolesAllowed("ROLE_TEACHER")
    @PreAuthorize("hasAuthority('TEACHER')")
    public ResponseEntity<CourseDto> createCourse(@RequestBody CourseFormCreateDto courseFormCreate){
        return tryHandle(() -> {
            CourseEntity course = courseService.createCourse(courseFormCreate);
            return new ResponseEntity<>(new CourseDto(course), HttpStatus.OK);
        });
    }

    @DeleteMapping("/{id}")
    //@RolesAllowed("TEACHER")
    @PreAuthorize("hasAuthority('TEACHER')")
    public void deleteCourse(@PathVariable("id") int id){
        courseService.deleteCourseById(id);
    }
}
