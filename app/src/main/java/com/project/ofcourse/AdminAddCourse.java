package com.project.ofcourse;

import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class AdminAddCourse extends AppCompatActivity {
    EditText editCode, editName, editSession, editPrereq;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_add_course);

        Button addCourse = findViewById(R.id.addCourseAdminButton);
        addCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // adds course details to firebase, updates course list
                editCode = (EditText) findViewById(R.id.courseCodeEditText);
                editName = (EditText) findViewById(R.id.courseNameEditText);
                editSession = (EditText) findViewById(R.id.offeringSessionEditText);
                editPrereq = (EditText) findViewById(R.id.prerequisiteEditText);
                newCourse(view);
                //backToDashboard();
            }
        });

        Button back = findViewById(R.id.backButton);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                backToDashboard();
            }
        });
    }
    public void backToDashboard(){
        Intent intent = new Intent(this, AdminDashboard.class);
        startActivity(intent);
    }

    public void newCourse(View view){

        String name = editName.getText().toString().trim();
        String code = editCode.getText().toString().trim();
        String session = editSession.getText().toString().trim();
        String prereq = editPrereq.getText().toString().trim();

        if(name.isEmpty()){
            editName.setError("Course name is required");
            editName.requestFocus();
            return;
        }
        if(code.isEmpty()){
            editCode.setError("Course code is required");
            editCode.requestFocus();
            return;
        }
        if(session.isEmpty()){
            editSession.setError("Course session is required");
            editSession.requestFocus();
            return;
        }

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference coursesRef = db.collection("courses");
        Course course = new Course (name, code, session, prereq);

        coursesRef.add(course)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Snackbar.make(view, "Course Successfully Added", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                        Log.d("TAG", "DocumentSnapshot added with ID: " + documentReference.getId());
                        course.id = documentReference.getId();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Snackbar.make(view, "Error: Course not Added", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                        Log.w("TAG", "Error adding document", e);
                    }
                });



    }
}
