package com.project.ofcourse;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
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
                editCode = (EditText) findViewById(R.id.courseCodeEditText);
                editName = (EditText) findViewById(R.id.courseNameEditText);
                editSession = (EditText) findViewById(R.id.offeringSessionEditText);
                editPrereq = (EditText) findViewById(R.id.prerequisiteEditText);

                DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
                ref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.hasChild(editCode.getText().toString().trim())){
                            // course already exists
                            updateCourse(view);
                        }
                        else { //course doesn't exist
                            newCourse(view);
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(getApplicationContext(), "Failed to add course",
                                Toast.LENGTH_LONG).show();
                    }
                });
                backToDashboard();
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

    public Course getCourseDetails(){
        String name = editName.getText().toString().trim();
        String code = editCode.getText().toString().trim();
        String session = editSession.getText().toString().trim();
        String prereq = editPrereq.getText().toString().trim();
        return new Course(name, code, session, prereq);
    }

    public void checkCourseDetails(Course course){
        if (course.getName().isEmpty()){
            editName.setError("Course name is required");
            editName.requestFocus();
            return;
        }
        if (course.getCode().isEmpty()){
            editCode.setError("Course code is required");
            editCode.requestFocus();
            return;
        }
        if (course.getSession().isEmpty()){
            editSession.setError("Course session is required");
            editSession.requestFocus();
            return;
        }
    }

    public void newCourse(View view){
        Course course = getCourseDetails();
        checkCourseDetails(course);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference coursesRef = db.collection("courses");

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

    public void updateCourse(View view){
        Course course = getCourseDetails();
        checkCourseDetails(course);

        Map<String, Object> map = new HashMap<>();
        map.put("name", course.getName());
        map.put("code", course.getCode());
        map.put("session", course.getSession());
        map.put("prereq", course.getPrereq());
        FirebaseDatabase.getInstance().getReference().child("courses")
                .updateChildren(map)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(getApplicationContext(), "Course Updated Successfully",
                                Toast.LENGTH_LONG).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(), "Error Updating Course",
                                Toast.LENGTH_LONG).show();
                    }
                });
    }
}
