package com.example.assignmentapp.service;

import com.example.assignmentapp.dto.LoginFormDto;
import com.example.assignmentapp.dto.LoginResultDto;
import com.example.assignmentapp.dto.UserFormCreateDto;
import com.example.assignmentapp.enumeration.UserExceptionType;
import com.example.assignmentapp.exceptions.UserException;
import com.example.assignmentapp.model.UserEntity;
import com.example.assignmentapp.repositories.IUserRepository;
import com.example.assignmentapp.security.JwtTokenProvider;
import com.example.assignmentapp.util.FileUploadUtil;
import com.example.assignmentapp.util.IAuthenticationFacade;
import com.example.assignmentapp.util.UserIdentity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.view.RedirectView;

import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Transactional
public class UserService {

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private IAuthenticationFacade authenticationFacade;


    public List<UserEntity> getAllUser() {
        return userRepository.findAll();
    }

    public UserEntity getUserById(int id) throws UserException {
        Optional<UserEntity> user = userRepository.findById(id);

        if(user.isEmpty()){
            throw new UserException(UserExceptionType.NOT_FOUND);
        }

        return user.get();
    }

    @Transactional
    public UserEntity createUser(UserFormCreateDto userFormCreateDto) throws UserException {
        UserEntity userLoginExists = userRepository.getUserByLogin(userFormCreateDto.getLogin());
        if (userLoginExists != null){
            throw new UserException(UserExceptionType.ALREADY_EXIST_CREATE);
        }
        else{
            userFormCreateDto.setPassword(passwordEncoder.encode(userFormCreateDto.getPassword()));
            return userRepository.save(new UserEntity(userFormCreateDto.getName(), userFormCreateDto.getFirstname(), userFormCreateDto.getLogin(), userFormCreateDto.getPassword(), userFormCreateDto.getRole()));
        }
    }

    @Transactional
    public void deleteUserById(Integer id){
        userRepository.deleteById(id);
    }

    public LoginResultDto signIn(LoginFormDto loginForm) throws UserException {

        String login = loginForm.getLogin();

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(login, loginForm.getPassword()));
            return jwtTokenProvider.createToken(login, userRepository.getUserByLogin(login).getRole());
        } catch (AuthenticationException e) {
            throw new UserException(UserExceptionType.INVALID_CREDENTIAL);
        }
    }

    public LoginResultDto refresh(String login) {
        return jwtTokenProvider.createToken(login, userRepository.getUserByLogin(login).getRole());
    }

    @Transactional
    public void savePicUser(int id, MultipartFile multipartFile) throws IOException, UserException {

        UserEntity user = this.getUserById(id);

        List<String> term = Arrays.asList("jpeg", "jpg", "png", "gif");

        boolean isOk = term.stream().anyMatch(t -> multipartFile.getOriginalFilename().toLowerCase().endsWith(t));

        if(isOk){
            user.setPicturename(multipartFile.getOriginalFilename());
            user.setPicturebytes(multipartFile.getBytes());
            user.setPicturecontenttype(multipartFile.getContentType());
            userRepository.saveAndFlush(user);
        }
    }

    public ResponseEntity<byte[]> getUserPic(int id) throws UserException {
        UserEntity user = this.getUserById(id);
        return ResponseEntity.ok().contentType(MediaType.parseMediaType(user.getPicturecontenttype())).body(user.getPicturebytes());
    }
}
