package com.example.assignmentapp.util;

import com.example.assignmentapp.model.UserEntity;
import org.springframework.security.core.Authentication;

public interface IAuthenticationFacade {
    Authentication getAuthentication();
    UserEntity getUser();
}