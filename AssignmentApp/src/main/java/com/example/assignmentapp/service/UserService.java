package com.example.assignmentapp.service;

import com.example.assignmentapp.dto.LoginFormDto;
import com.example.assignmentapp.dto.LoginResultDto;
import com.example.assignmentapp.exceptions.CustomException;
import com.example.assignmentapp.exceptions.UserException;
import com.example.assignmentapp.model.UserEntity;
import com.example.assignmentapp.repositories.IUserRepository;
import com.example.assignmentapp.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
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


    public List<UserEntity> getAllUser() {
        return userRepository.findAll();
    }

    public UserEntity getUserById(int id) throws UserException {
        Optional<UserEntity> user = userRepository.findById(id);

        if(user.isEmpty()){
            throw new UserException();
        }

        return user.get();
    }

    @Transactional
    public UserEntity createUser(UserEntity user) throws UserException {
        UserEntity userLoginExists = userRepository.getUserByLogin(user.getLogin());
        if (userLoginExists != null){
            throw new UserException();
        }
        else{
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            return userRepository.save(user);
        }
    }

    @Transactional
    public void deleteUserById(Integer id){
        userRepository.deleteById(id);
    }

    public LoginResultDto signIn(LoginFormDto loginForm) {

        String login = loginForm.getLogin();

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(login, loginForm.getPassword()));
            return jwtTokenProvider.createToken(login, userRepository.getUserByLogin(login).getRole());
        } catch (AuthenticationException e) {
            throw new CustomException("Invalid username/password supplied", HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    public LoginResultDto refresh(String login) {
        return jwtTokenProvider.createToken(login, userRepository.getUserByLogin(login).getRole());
    }

}
