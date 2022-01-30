package com.example.assignmentapp.util;

import org.springframework.security.core.Authentication;

public interface IAuthenticationFacade {
    Authentication getAuthentication();
    UserIdentity getUser();
}