package com.example.assignmentapp.service;

import com.example.assignmentapp.dto.*;
import com.example.assignmentapp.enumeration.EnumWorkStatus;
import com.example.assignmentapp.enumeration.WorkExceptionType;
import com.example.assignmentapp.exceptions.AssignmentException;
import com.example.assignmentapp.exceptions.UserException;
import com.example.assignmentapp.exceptions.WorkException;
import com.example.assignmentapp.model.AssignmentEntity;
import com.example.assignmentapp.model.CourseEntity;
import com.example.assignmentapp.model.UserEntity;
import com.example.assignmentapp.model.WorkEntity;
import com.example.assignmentapp.repositories.IWorkRepository;
import com.example.assignmentapp.util.IAuthenticationFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
    public WorkEntity createWork(WorkFormCreateDto workFormCreateDto) throws UserException, AssignmentException, WorkException {

        //on recupere l'idass et l'iduser passes dans le formulaire
        int authIdUser = authenticationFacade.getUser().getIduser();
        UserEntity user = userService.getUserById(authIdUser);
        AssignmentEntity assignment = assignmentService.getAssigmentById(workFormCreateDto.getIdAss());

        if(assignment.getIsclosed()){
            throw new WorkException(WorkExceptionType.ASSIGNMENT_CLOSED);
        }

        WorkDto entity = this.getWorkStudentByIdAssignment(assignment.getIdass());

        if(entity != null){
            throw new WorkException(WorkExceptionType.STUDENT_HAVE_ALREADY_WORK);
        }

        return workRepository.save(new WorkEntity(workFormCreateDto, user, assignment));
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

    public List<WorkDto> getAllWorkPagination(WorkSearchFormDto form) {
        Specification<WorkEntity> filterStatus = (root, query, cb) -> cb.like(root.get("status"), form.getStatus());
        Specification<WorkEntity> ofAss = (root, query, cb) -> cb.equal(root.get("assignmentEntity").get("idass"), form.getIdAss());
        return workRepository.findAll(Specification.where(filterStatus).and(ofAss)).stream().map(WorkDto::new).collect(Collectors.toList());
    }

    public List<WorkEntity> getWorksByIdUser(int idUser){
       return workRepository.getAllWorksByIdUser(idUser);
    }
}
