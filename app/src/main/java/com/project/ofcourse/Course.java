package com.project.ofcourse;

public class Course {
    String courseName;
    String courseCode;

    Course [] prerequisites;
    String [] offeringSession;

    public Course(String name, String code){
        courseName = name;
        courseCode = code;

    }
    public Course searchCourse(Course [] courses, String name){
        for (Course c: courses){
            if (c.courseCode.equalsIgnoreCase(name)){
                return c;
            }
            else {
                // somehow tell the user that the course wasn't found
            }
        }
        return null;
    }
}
