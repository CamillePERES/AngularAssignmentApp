package com.example.assignmentapp.model;

import org.springframework.security.core.GrantedAuthority;

public enum UserRoleEntity implements GrantedAuthority {
    STUDENT, TEACHER;

    public String getAuthority() {
        return name();
    }

}
