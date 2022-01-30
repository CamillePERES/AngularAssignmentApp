package com.example.assignmentapp.service;

import com.example.assignmentapp.dto.WorkFormCreate;
import com.example.assignmentapp.dto.WorkFormEvaluation;
import com.example.assignmentapp.enumeration.EnumWorkStatus;
import com.example.assignmentapp.exceptions.EntityNotFoundException;
import com.example.assignmentapp.exceptions.UserException;
import com.example.assignmentapp.exceptions.WorkException;
import com.example.assignmentapp.model.AssignmentEntity;
import com.example.assignmentapp.model.UserEntity;
import com.example.assignmentapp.model.WorkEntity;
import com.example.assignmentapp.repositories.IWorkRepository;
import org.hibernate.jdbc.Work;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class WorkService {

    @Autowired
    private IWorkRepository workRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private AssignmentService assignmentService;

    public List<WorkEntity> getAllWork() {
        return workRepository.findAll();
    }

    public WorkEntity getWorkById(int id) throws WorkException {
        Optional<WorkEntity> work = workRepository.findById(id);

        if(work.isEmpty()){
            throw new WorkException();
        }

        return work.get();
    }

    @Transactional
    public WorkEntity createWork(WorkFormCreate workFormCreate) throws UserException, EntityNotFoundException, WorkException {

        //on recupere l'idass et l'iduser passes dans le formulaire
        UserEntity user = userService.getUserById(workFormCreate.getIdUser());
        AssignmentEntity assignment = assignmentService.getAssigmentById(workFormCreate.getIdAss());

        if(user == null && assignment == null){
            throw new EntityNotFoundException();
        }

        Collection<WorkEntity> courses = assignment.getWorks();
        boolean alreadyHaveAName = courses
                .stream()
                .anyMatch(wk -> wk.getName().equals(workFormCreate.getName()));

        if(alreadyHaveAName){
            throw new WorkException();
        }

        return workRepository.save(new WorkEntity(workFormCreate.getName(), workFormCreate.getDescription(), 0, "",  EnumWorkStatus.Submitted.name(), user, assignment));
    }

    /*@Transactional
    public WorkEntity updateWorkForEvaluation(WorkFormEvaluation workFormEvaluation) throws WorkException, EntityNotFoundException {

        WorkEntity wk = this.getWorkById(workFormEvaluation.getIdWork());

        if(wk == null){
            throw new EntityNotFoundException();
        }

        int idUser = wk.getAssignmentEntity().getCourseEntity().getUserEntity().getIduser();
        //comparer iduser de la matiere, de l'assignement du work avec le user connecte
        //si true (ce sont les memes), update les champs status, comment, grade (workformevaluation)
        //si false, renvoie une exception

    }*/

    @Transactional
    public void deleteWorkById(Integer id){
        workRepository.deleteById(id);
    }
}
