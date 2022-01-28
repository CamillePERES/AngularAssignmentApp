package com.example.assignmentapp.enumeration;

public enum EnumRole {

    Student("Student"),Teacher("Teacher");

    public String role;

    private EnumRole(String role){
        this.role=role;
    }
}
