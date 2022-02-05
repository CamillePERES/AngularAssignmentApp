package com.example.assignmentapp.service;

import com.example.assignmentapp.dto.AssignmentFormCreateDto;
import com.example.assignmentapp.dto.AssignmentFormUpdateDto;
import com.example.assignmentapp.dto.AssignmentFormUpdateResultDto;
import com.example.assignmentapp.enumeration.AssignmentExceptionType;
import com.example.assignmentapp.exceptions.AssignmentException;
import com.example.assignmentapp.exceptions.CourseException;
import com.example.assignmentapp.model.AssignmentEntity;
import com.example.assignmentapp.model.CourseEntity;
import com.example.assignmentapp.repositories.IAssignmentRepository;
import com.example.assignmentapp.util.IAuthenticationFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
    public AssignmentEntity createAssignment(AssignmentFormCreateDto assignmentFormCreate) throws AssignmentException, EntityNotFoundException, CourseException {

        //user actuellement log
        int authIdUser = authenticationFacade.getUser().getIduser();

        Optional<AssignmentEntity> oass = assignmentRepository.findById(assignmentFormCreate.getIdAss());

        if (oass.isPresent()) {
            throw new AssignmentException();
        }

        //recupere l'id de la matiere qu'on met dans le form
        CourseEntity course = courseService.getCourseById(assignmentFormCreate.getCourseId());

        if (course == null) {
            throw new EntityNotFoundException();
        }

        if (course.getUserEntity().getIduser() != authIdUser) {
            throw new AssignmentException();
        }

        //test s'il y a deja un assignement avec le meme nom dans la matiere
        Collection<AssignmentEntity> assignments = course.getAssignments();
        boolean haveAlreadyName = assignments
                .stream()
                .anyMatch(assignment -> assignment.getName().equals(assignmentFormCreate.getName()));

        if (haveAlreadyName) {
            throw new AssignmentException();
        }

        return assignmentRepository.save(new AssignmentEntity(
                assignmentFormCreate.getName(),
                assignmentFormCreate.getDate(),
                assignmentFormCreate.getDescription(),
                course
        ));
    }

    @Transactional
    public AssignmentEntity updateAssignment(AssignmentFormUpdateDto assignmentFormUpdateDto) throws EntityNotFoundException {

        AssignmentEntity ass = this.getAssigmentById(assignmentFormUpdateDto.getIdAss());

        if(ass == null){
            throw new EntityNotFoundException();
        }

        return assignmentRepository.save(new AssignmentFormUpdateResultDto(assignmentFormUpdateDto.getName(), assignmentFormUpdateDto.getDate(), assignmentFormUpdateDto.getDescription()));
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
}
