package com.example.assignmentapp.service;

import com.example.assignmentapp.exceptions.UserException;
import com.example.assignmentapp.model.UserEntity;
import com.example.assignmentapp.repositories.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserService {

    @Autowired
    private IUserRepository userRepository;

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
            return userRepository.save(user);
        }
    }

    @Transactional
    public void deleteUserById(Integer id){
        userRepository.deleteById(id);
    }

}
