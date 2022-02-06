package com.example.assignmentapp.service;

import com.example.assignmentapp.dto.AssignmentFormCreateDto;
import com.example.assignmentapp.dto.AssignmentFormUpdateDto;
import com.example.assignmentapp.enumeration.AssignmentExceptionType;
import com.example.assignmentapp.enumeration.CourseExceptionType;
import com.example.assignmentapp.exceptions.AssignmentException;
import com.example.assignmentapp.exceptions.CourseException;
import com.example.assignmentapp.model.AssignmentEntity;
import com.example.assignmentapp.model.CourseEntity;
import com.example.assignmentapp.repositories.IAssignmentRepository;
import com.example.assignmentapp.util.IAuthenticationFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class AssignmentService {

    @Autowired
    private CourseService courseService;

    @Autowired
    private IAssignmentRepository assignmentRepository;

    @Autowired
    private IAuthenticationFacade authenticationFacade;

    public List<AssignmentEntity> getAllAssignment() {
        return assignmentRepository.findAll();
    }

    public AssignmentEntity getAssigmentById(int id) throws AssignmentException {
        Optional<AssignmentEntity> assignment = assignmentRepository.findById(id);

        if (assignment.isEmpty()) {
            throw new AssignmentException(AssignmentExceptionType.NOT_FOUND);
        }

        return assignment.get();
    }

    @Transactional
    public AssignmentEntity createAssignment(AssignmentFormCreateDto assignmentFormCreate) throws AssignmentException, CourseException {

        //user actuellement log
        int authIdUser = authenticationFacade.getUser().getIduser();

        //recupere l'id de la matiere qu'on met dans le form
        CourseEntity course = courseService.getCourseById(assignmentFormCreate.getCourseId());

        if (course.getUserEntity().getIduser() != authIdUser) {
            throw new CourseException(CourseExceptionType.USER_NOT_OWNER);
        }

        //test s'il y a deja un assignement avec le meme nom dans la matiere
        Collection<AssignmentEntity> assignments = course.getAssignments();
        boolean haveAlreadyName = assignments
                .stream()
                .anyMatch(assignment -> assignment.getName().equals(assignmentFormCreate.getName()));

        if (haveAlreadyName) {
            throw new AssignmentException(AssignmentExceptionType.ALREADY_EXIST_CREATE);
        }

        return assignmentRepository.save(new AssignmentEntity(
                assignmentFormCreate.getName(),
                assignmentFormCreate.getDate(),
                assignmentFormCreate.getDescription(),
                course
        ));
    }

    @Transactional
    public void deleteAssignmentById(Integer id) {
        assignmentRepository.deleteById(id);
    }

    @Transactional
    public List<AssignmentEntity> getAllAssignmentsPagination(Integer pageNo, Integer pageSize) {

        Pageable paging = PageRequest.of(pageNo, pageSize);

        Page<AssignmentEntity> pagedResult = assignmentRepository.findAll(paging);

        if (pagedResult.hasContent()) {
            return pagedResult.getContent();
        } else {
            return new ArrayList<>();
        }
    }

    @Transactional
    public AssignmentEntity updateAssignment(AssignmentFormUpdateDto assignmentFormUpdateDto) throws AssignmentException, CourseException {

        AssignmentEntity ass = this.getAssigmentById(assignmentFormUpdateDto.getIdAss());

        int idUser = ass.getCourseEntity().getUserEntity().getIduser();
        int authIdUser = authenticationFacade.getUser().getIduser();

        if(idUser != authIdUser){
            throw new CourseException(CourseExceptionType.USER_NOT_OWNER);
        }

        ass.setName(assignmentFormUpdateDto.getName());
        ass.setDescription(assignmentFormUpdateDto.getDescription());
        ass.setDate(assignmentFormUpdateDto.getDate());
        ass.setIsclosed(assignmentFormUpdateDto.isIsclosed());

        return assignmentRepository.saveAndFlush(ass);
    }

    /*public List<AssignmentEntity> getAssigmnentByIdCourse(int idCourse) {

        Specification<AssignmentEntity> spec = (root, query, cb) -> cb.equal(root.get("courseEntity").get("idcourse"), idCourse);
        return assignmentRepository.findAll(spec);
    }*/

    public List<AssignmentEntity> getAssigmnentByIdCourse(int idCourse) {
       return assignmentRepository.getAllAssignmentByIdCourse(idCourse);
    }
}
