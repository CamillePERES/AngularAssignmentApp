package com.example.assignmentapp.util;

import com.example.assignmentapp.repositories.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationFacade implements IAuthenticationFacade {

    @Autowired
    private IUserRepository userRepository;

    @Override
    public Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    @Override
    public UserIdentity getUser() {
        User principal = (User) this.getAuthentication().getPrincipal();
        return new UserIdentity(userRepository.getUserByLogin(principal.getUsername()));
    }
}