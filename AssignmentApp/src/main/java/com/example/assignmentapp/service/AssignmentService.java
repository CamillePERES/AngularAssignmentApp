package com.example.assignmentapp.service;

import com.example.assignmentapp.dto.AssignmentFormCreate;
import com.example.assignmentapp.exceptions.AssignmentException;
import com.example.assignmentapp.exceptions.CourseException;
import com.example.assignmentapp.exceptions.EntityNotFoundException;
import com.example.assignmentapp.model.AssignmentEntity;
import com.example.assignmentapp.model.CourseEntity;
import com.example.assignmentapp.repositories.IAssignmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
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

    public List<AssignmentEntity> getAllAssignment() {
        return assignmentRepository.findAll();
    }

    public AssignmentEntity getAssigmentById(int id) throws EntityNotFoundException {
        Optional<AssignmentEntity> assignment = assignmentRepository.findById(id);

        if(assignment.isEmpty()){
            throw new EntityNotFoundException();
        }

        return assignment.get();
    }

   @Transactional
    public AssignmentEntity createAssignment(AssignmentFormCreate assignmentFormCreate) throws AssignmentException, EntityNotFoundException, CourseException {

        CourseEntity entity = courseService.getCourseById(assignmentFormCreate.getCourseId());

        if(entity == null){
           throw new EntityNotFoundException();
        }

        //test s'il y a deja un assignement avec le meme nom dans la matiere
        Collection<AssignmentEntity> assignments = entity.getAssignments();
        boolean haveAlreadyName = assignments
                .stream()
                .anyMatch(assignment -> assignment.getName().equals(assignmentFormCreate.getName()));

        if(haveAlreadyName){
            throw new AssignmentException();
        }

       return assignmentRepository.save(new AssignmentEntity(
               assignmentFormCreate.getName(),
               assignmentFormCreate.getDate(),
               entity
       ));
    }

    @Transactional
    public void deleteAssignmentById(Integer id){
        assignmentRepository.deleteById(id);
    }
}
