package com.example.assignmentapp.service;

import com.example.assignmentapp.dto.*;
import com.example.assignmentapp.enumeration.CourseExceptionType;
import com.example.assignmentapp.exceptions.CourseException;
import com.example.assignmentapp.exceptions.UserException;
import com.example.assignmentapp.model.CourseEntity;
import com.example.assignmentapp.model.UserEntity;
import com.example.assignmentapp.repositories.ICourseRepository;
import com.example.assignmentapp.util.IAuthenticationFacade;
import com.example.assignmentapp.util.UserIdentity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

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
            throw new CourseException(CourseExceptionType.NOT_FOUND);
        }

        return course.get();
    }

    @Transactional
    public CourseEntity createCourse(CourseFormCreateDto courseFormCreate) throws CourseException, UserException {

        //recuperer le user actuellement connecte
        UserIdentity authIdUser = authenticationFacade.getUser();

        //recuperer l'id de l'user qui a ete passe dans le formulaire de creation de matiere
        UserEntity user = userService.getUserById(authIdUser.getIduser());

        //test s'il y a deja une matiere avec le meme nom pour ce user
        Collection<CourseEntity> courses = user.getCourses();
        boolean haveAlreadyAName = courses
                .stream()
                //on comparer les noms ds la liste des matieres avec celui que je rentre dans le formulaire
                .anyMatch(course -> course.getName().equals(courseFormCreate.getName()));

        if(haveAlreadyAName){
            throw new CourseException(CourseExceptionType.ALREADY_EXIST_CREATE);
        }

        return courseRepository.save(new CourseEntity(courseFormCreate.getName(), user, courseFormCreate.getDescription()));
    }

    @Transactional
    public void deleteCourseById(Integer id){
        courseRepository.deleteById(id);
    }

    @Transactional
    public CourseEntity updateCourse(CourseFormUpdateDto courseFormUpdateDto) throws CourseException {

        CourseEntity cs = this.getCourseById(courseFormUpdateDto.getIdCourse());

        int idUser = cs.getUserEntity().getIduser();
        int authIdUser = authenticationFacade.getUser().getIduser();

        if(idUser != authIdUser){
            throw new CourseException(CourseExceptionType.USER_NOT_OWNER);
        }

        cs.setName(courseFormUpdateDto.getName());
        cs.setDescription(courseFormUpdateDto.getDescription());

        return courseRepository.save(cs);
    }

    public PaginationResult<CourseDto> getAllCoursesPagination(CourseSearchForm form) {

        String courseName = form.getCourseName().trim();
        String userName = form.getUserName().trim();
        Pageable paging = PageRequest.of(form.getPage()-1, form.getPageSize());
        Specification<CourseEntity> spec1 = (root, query, cb) -> cb.like(root.get("name"), "%"+courseName+"%");
        Specification<CourseEntity> spec2 = (root, query, cb) -> cb.like(root.get("userEntity").get("name"), "%"+userName+"%");
        Specification<CourseEntity> spec3 = (root, query, cb) -> cb.like(root.get("userEntity").get("firstname"), "%"+userName+"%");
        Specification<CourseEntity> spec4 = (root, query, cb) -> cb.like(root.get("userEntity").get("login"), "%"+userName+"%");
        Specification<CourseEntity> course = null;
        Specification<CourseEntity> user = null;

        if(!courseName.isEmpty()){
            course = Specification.where(spec1);
        }

        if(!userName.isEmpty()){
            user = Specification.where(spec2).or(spec3).or(spec4);
        }

        Specification<CourseEntity> finalSpec = course != null ?
                user != null ? course.and(user) : course :
                user;

        Page<CourseEntity> entities = courseRepository.findAll(finalSpec, paging);
        List<CourseDto> courses = entities.get().map(CourseDto::new).collect(Collectors.toList());

        PaginationResult<CourseDto> results = new PaginationResult<>();
        results.setPage(form.getPage());
        results.setPageSize(form.getPageSize());
        results.setTotal((int) courseRepository.count());
        results.setResults(courses);
        results.setTotalPage(courses.size());

        return results;
    }

    @Transactional
    public void savePicCourse(int id, MultipartFile multipartFile) throws IOException, CourseException {

        CourseEntity course = this.getCourseById(id);

        List<String> term = Arrays.asList("jpeg", "jpg", "png", "gif");

        boolean isOk = term.stream().anyMatch(t -> multipartFile.getOriginalFilename().toLowerCase().endsWith(t));

        if(isOk){
            course.setPicturename(multipartFile.getOriginalFilename());
            course.setPicturebytes(multipartFile.getBytes());
            course.setPicturecontenttype(multipartFile.getContentType());
            courseRepository.save(course);
        }
    }

    public ResponseEntity<byte[]> getCoursePic(int id) throws CourseException {
        CourseEntity course = this.getCourseById(id);
        return ResponseEntity.ok().contentType(MediaType.parseMediaType(course.getPicturecontenttype())).body(course.getPicturebytes());
    }
}
