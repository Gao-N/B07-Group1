package com.project.ofcourse;

import java.util.Objects;

public class Student {
    String email;
    String password;
    String firstName;
    String lastName;

    //Course [] pastCourses;

    // Constructor for login
    public Student(String email, String password) {
        this.email = email;
        this.password = password;
    }

    // Constructor for sign up
    public Student(String email, String password, String firstName, String lastName) {
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return Objects.equals(email, student.email) && Objects.equals(password, student.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email, password);
    }
}
