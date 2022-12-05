package com.project.ofcourse;

import androidx.annotation.NonNull;
import java.util.ArrayList;

//import com.google.firebase.database.DataSnapshot;
//import com.google.firebase.database.DatabaseError;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.database.ValueEventListener;
=========
import java.util.Objects;
>>>>>>>>> Temporary merge branch 2

public class Student {
    String email;
    String name;
    ArrayList<String> past_courses;

    public Student(){}

    // Constructor for sign up
    public Student(String email, String firstName, String lastName) {
        this.email = email;
        this.name = firstName + " " + lastName;
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
