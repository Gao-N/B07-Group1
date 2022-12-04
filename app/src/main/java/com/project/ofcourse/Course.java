package com.project.ofcourse;

import java.util.ArrayList;

public class Course {
    public String name, code, session, prereq, id;

    public Course(){

    }
    public Course(String name, String code, String session, String prereq){

        this.name = name;
        this.code = code;
        this.session = session;
        this.prereq = prereq;
    }

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }

    public String getSession() {
        return session;
    }

    public String getPrereq() {
        return prereq;
    }
}
