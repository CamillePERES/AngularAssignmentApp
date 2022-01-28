package com.example.assignmentapp.service;

import com.example.assignmentapp.dto.CourseFormCreate;
import com.example.assignmentapp.exceptions.CourseException;
import com.example.assignmentapp.exceptions.EntityNotFoundException;
import com.example.assignmentapp.exceptions.UserException;
import com.example.assignmentapp.model.AssignmentEntity;
import com.example.assignmentapp.model.CourseEntity;
import com.example.assignmentapp.model.UserEntity;
import com.example.assignmentapp.repositories.ICourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CourseService {

    @Autowired
    ICourseRepository courseRepository;

    @Autowired
    UserService userService;

    public List<CourseEntity> getAllCourses() {
        return courseRepository.findAll();
    }

    public CourseEntity getCourseById(int id) throws CourseException {
        Optional<CourseEntity> course = courseRepository.findById(id);

        if(course.isEmpty()){
            throw new CourseException();
        }

        return course.get();
    }

    @Transactional
    public CourseEntity createCourse(CourseFormCreate courseFormCreate) throws EntityNotFoundException, CourseException, UserException {

        //recuperer l'id de l'user qui a ete passe dans le formulaire de creation de matiere
        UserEntity user = userService.getUserById(courseFormCreate.getIdUser());

        if(user == null){
            throw new EntityNotFoundException();
        }

        //test s'il y a deja une matiere avec le meme nom pour ce user
        Collection<CourseEntity> courses = user.getCourses();
        boolean haveAlreadyAName = courses
                .stream()
                //on comparer les noms ds la liste des matieres avec celui que je rentre dans le formulaire
                .anyMatch(course -> course.getName().equals(courseFormCreate.getName()));

        if(haveAlreadyAName){
            throw new CourseException();
        }

        return courseRepository.save(new CourseEntity(courseFormCreate.getName(), user));
    }

    @Transactional
    public void deleteCourseById(Integer id){
        courseRepository.deleteById(id);
    }

}
