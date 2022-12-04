package com.project.ofcourse;

import androidx.annotation.NonNull;
import java.util.ArrayList;

//import com.google.firebase.database.DataSnapshot;
//import com.google.firebase.database.DatabaseError;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.database.ValueEventListener;

public class Student {
    String email;
    ArrayList<String> pastCourses;
    String password;
    String firstName;
    String lastName;

    //Course [] pastCourses;

    // Constructor for login
    public Student(String email, String password) {
        this.email = email;
        this.password = password;
        this.pastCourses = new ArrayList<>();
    }

    // Constructor for sign up
    public Student(String email, String password, String firstName, String lastName) {
        this.email = email;
        this.password = password;
        this.firstName = firstName;

        this.lastName = lastName;
    }

}
