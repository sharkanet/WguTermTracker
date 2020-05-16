package com.chris.chriswgutermtracker.utility;

public enum CourseStatus{
    IN_PROGRESS("In Progress"),
    COMPLETED("Completed"),
    DROPPED("Dropped"),
    PLAN_TO_TAKE("Plan To Take");

    private CourseStatus(String string){
        this.string = string;
    }

    private String string;

    @Override
    public String toString(){
        return string;
    }
}