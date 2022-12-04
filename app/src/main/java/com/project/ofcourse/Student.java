package com.project.ofcourse;

import java.util.Objects;

public class Student {
    String email;
    String firstName;
    String name;
    String lastName;
    String[] past_courses;

    public Student(){}

    // Constructor for sign up
    public Student(String email, String firstName, String lastName) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.name = firstName + " " + lastName;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {return email;}

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

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
