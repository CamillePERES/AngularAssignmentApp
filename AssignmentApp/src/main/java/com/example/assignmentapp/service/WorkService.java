package com.example.assignmentapp.service;

import com.example.assignmentapp.dto.WorkFormCreateDto;
import com.example.assignmentapp.dto.WorkFormEvaluationDto;
import com.example.assignmentapp.enumeration.EnumWorkStatus;
import com.example.assignmentapp.exceptions.EntityNotFoundException;
import com.example.assignmentapp.exceptions.UserException;
import com.example.assignmentapp.exceptions.WorkException;
import com.example.assignmentapp.model.AssignmentEntity;
import com.example.assignmentapp.model.UserEntity;
import com.example.assignmentapp.model.WorkEntity;
import com.example.assignmentapp.repositories.IWorkRepository;
import com.example.assignmentapp.util.IAuthenticationFacade;
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

    @Autowired
    private IAuthenticationFacade authenticationFacade;

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
    public WorkEntity createWork(WorkFormCreateDto workFormCreateDto) throws UserException, EntityNotFoundException, WorkException {

        //on recupere l'idass et l'iduser passes dans le formulaire
        UserEntity user = userService.getUserById(workFormCreateDto.getIdUser());
        AssignmentEntity assignment = assignmentService.getAssigmentById(workFormCreateDto.getIdAss());

        if(user == null && assignment == null){
            throw new EntityNotFoundException();
        }

        Collection<WorkEntity> courses = assignment.getWorks();
        boolean alreadyHaveAName = courses
                .stream()
                .anyMatch(wk -> wk.getName().equals(workFormCreateDto.getName()));

        if(alreadyHaveAName){
            throw new WorkException();
        }

        return workRepository.save(new WorkEntity(workFormCreateDto.getName(), workFormCreateDto.getDescription(), 0, "",  EnumWorkStatus.Submitted.name(), user, assignment));
    }

    @Transactional
    public WorkEntity updateWorkForEvaluation(WorkFormEvaluationDto workFormEvaluation) throws WorkException, EntityNotFoundException {

        WorkEntity wk = this.getWorkById(workFormEvaluation.getIdWork());

        if(wk == null){
            throw new EntityNotFoundException();
        }

        int idUser = wk.getAssignmentEntity().getCourseEntity().getUserEntity().getIduser();
        int authIdUser = authenticationFacade.getUser().getIduser();

        if (idUser != authIdUser){
            throw new WorkException();
        }

        if(wk.getStatus().equals(EnumWorkStatus.Submitted.name()))

        wk.setGrade(workFormEvaluation.getGrade());
        wk.setComment(workFormEvaluation.getComment());
        wk.setStatus(EnumWorkStatus.Evaluated.name());

        return workRepository.saveAndFlush(wk);
    }

    @Transactional
    public void deleteWorkById(Integer id){
        workRepository.deleteById(id);
    }
}
