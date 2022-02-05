package com.example.assignmentapp.service;

import com.example.assignmentapp.dto.CourseFormCreateDto;
import com.example.assignmentapp.dto.CourseFormUpdateDto;
import com.example.assignmentapp.exceptions.CourseException;
import com.example.assignmentapp.exceptions.EntityNotFoundException;
import com.example.assignmentapp.exceptions.UserException;
import com.example.assignmentapp.model.CourseEntity;
import com.example.assignmentapp.model.UserEntity;
import com.example.assignmentapp.repositories.ICourseRepository;
import com.example.assignmentapp.util.IAuthenticationFacade;
import com.example.assignmentapp.util.UserIdentity;
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

    @Autowired
    private IAuthenticationFacade authenticationFacade;

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
    public CourseEntity createCourse(CourseFormCreateDto courseFormCreate) throws EntityNotFoundException, CourseException, UserException {

        //recuperer le user actuellement connecte
        UserIdentity authIdUser = authenticationFacade.getUser();

        //recuperer l'id de l'user qui a ete passe dans le formulaire de creation de matiere
        UserEntity user = userService.getUserById(courseFormCreate.getIdUser());

        if(user.getIduser() != authIdUser.getIduser()){
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

        return courseRepository.save(new CourseEntity(courseFormCreate.getName(), user, courseFormCreate.getDescription()));
    }

    @Transactional
    public void deleteCourseById(Integer id){
        courseRepository.deleteById(id);
    }

    @Transactional
    public CourseEntity updateCourse(CourseFormUpdateDto courseFormUpdateDto) throws CourseException, EntityNotFoundException {

        CourseEntity cs = this.getCourseById(courseFormUpdateDto.getIdcourse());

        if(cs == null){
            throw new EntityNotFoundException();
        }

        int idUser = cs.getUserEntity().getIduser();
        int authIdUser = authenticationFacade.getUser().getIduser();

        if(idUser != authIdUser){
            throw new CourseException();
        }

        cs.setName(courseFormUpdateDto.getName());
        cs.setDescription(courseFormUpdateDto.getDescription());

        return courseRepository.saveAndFlush(cs);
    }

}
