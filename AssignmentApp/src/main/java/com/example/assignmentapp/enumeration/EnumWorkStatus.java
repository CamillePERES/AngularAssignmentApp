package com.example.assignmentapp.enumeration;

public enum EnumWorkStatus {

    Submitted("Submitted"),Evaluated("Evaluated");

    public String status;

    private EnumWorkStatus(String status){
        this.status=status;
    }
}
