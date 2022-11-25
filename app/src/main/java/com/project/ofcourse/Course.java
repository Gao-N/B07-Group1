package com.project.ofcourse;

public class Course {
    public String name, code, session, prereq;

    public Course(){

    }
    public Course(String name, String code, String session, String prereq){
        this.name = name;
        this.code = code;
        this.session = session;
        this.prereq = prereq;

    }
}
