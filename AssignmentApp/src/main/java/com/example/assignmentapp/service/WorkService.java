package com.example.assignmentapp.service;

import com.example.assignmentapp.dto.WorkDto;
import com.example.assignmentapp.dto.WorkFormCreateDto;
import com.example.assignmentapp.dto.WorkFormEvaluationDto;
import com.example.assignmentapp.dto.WorkFormUpdateDto;
import com.example.assignmentapp.enumeration.EnumWorkStatus;
import com.example.assignmentapp.enumeration.WorkExceptionType;
import com.example.assignmentapp.exceptions.AssignmentException;
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
            throw new WorkException(WorkExceptionType.NOT_FOUND);
        }

        return work.get();
    }

    @Transactional
    public WorkEntity createWork(WorkFormCreateDto workFormCreateDto) throws UserException, WorkException, AssignmentException {

        //on recupere l'idass et l'iduser passes dans le formulaire
        UserEntity user = userService.getUserById(workFormCreateDto.getIdUser());
        AssignmentEntity assignment = assignmentService.getAssigmentById(workFormCreateDto.getIdAss());

        return workRepository.save(new WorkEntity(workFormCreateDto.getName(), workFormCreateDto.getDescription(), 0, "",  EnumWorkStatus.Submitted.name(), user, assignment));
    }

    @Transactional
    public WorkEntity updateWorkForEvaluation(WorkFormEvaluationDto workFormEvaluation) throws WorkException{

        WorkEntity wk = this.getWorkById(workFormEvaluation.getIdWork());

        int idUser = wk.getAssignmentEntity().getCourseEntity().getUserEntity().getIduser();
        int authIdUser = authenticationFacade.getUser().getIduser();

        if (idUser != authIdUser){
            throw new WorkException(WorkExceptionType.NOT_OWNER_OF_COURSE);
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

    @Transactional
    public WorkEntity updateWork(WorkFormUpdateDto workFormUpdateDto) throws WorkException {
        WorkEntity wrk = this.getWorkById(workFormUpdateDto.getIdWork());

        int idUser = wrk.getUserEntity().getIduser();
        int authIdUser = authenticationFacade.getUser().getIduser();

        if (idUser != authIdUser){
            throw new WorkException(WorkExceptionType.NOT_OWNER_OF_WORK);
        }

        if(wrk.getStatus().equals(EnumWorkStatus.Submitted.name()))

        wrk.setName(workFormUpdateDto.getName());
        wrk.setDescription(workFormUpdateDto.getDescription());

        return workRepository.saveAndFlush(wrk);
    }

    @Transactional
    public List<WorkEntity> getWorksByIdAss(int idAssignment) {
        return workRepository.getAllWorksByIdAss(idAssignment);
    }

    public WorkDto getWorkStudentByIdAssignment(int id) {
        int authIdUser = authenticationFacade.getUser().getIduser();
        WorkEntity entity = workRepository.getWorkStudentByIdAssignment(id, authIdUser);
        return entity == null ? null : new WorkDto(entity);
    }
}
