package com.project.ofcourse;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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

    public int login() {
        /*
        // "URL" needs to be changed
        DatabaseReference ref = FirebaseDatabase.getInstance("URL").getReference("students");
        final boolean[] studentFound = {false};

        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot child:snapshot.getChildren()) {
                    Student student = child.getValue(Student.class);
                    if (student.email.equals(Student.this.email) && student.password.equals(Student.this.password)) {
                        studentFound[0] = true;
                        break;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        ref.addValueEventListener(listener);
        */
        if (false) {
            return 1;
        }
        else {
            return 0;
        }
    }
}
