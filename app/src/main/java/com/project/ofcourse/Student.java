package com.project.ofcourse;

import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;

public class Student {
    public String email;
    public String name;
    public ArrayList<String> past_courses;
    public Map<String, ArrayList<String>> timeline;
    static String currentUser;

    public Student(){}

    // Constructor for sign up
    public Student(String email, String firstName, String lastName) {
        this.email = email;
        this.name = firstName + " " + lastName;
        this.past_courses = new ArrayList<>();
        past_courses.add("empty");

    }

    public String getName() {
        return name;
    }

    public String getEmail() {return email;}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return Objects.equals(email, student.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email);
    }
}
