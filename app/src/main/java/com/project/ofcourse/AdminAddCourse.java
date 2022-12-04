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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

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

                FirebaseFirestore rootRef = FirebaseFirestore.getInstance();
                CollectionReference usersRef = rootRef.collection("courses");
                Query query = usersRef.whereEqualTo("code", editCode.getText().toString().trim());
                query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (DocumentSnapshot document : task.getResult()) {
                                if (document.exists()) {
                                    updateCourse(view, document);
                                }
                                else {
                                    return;
                                }
                            }
                        } else {
                            Toast.makeText(getApplicationContext(), "Error",
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                });
                newCourse(view);
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

    public void newCourse(View view){
        Course course = getCourseDetails();
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

    public void updateCourse(View view, DocumentSnapshot document){
        Course course = getCourseDetails();
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

        Map<String, Object> map = new HashMap<>();
        map.put("name", course.getName());
        map.put("code", course.getCode());
        map.put("session", course.getSession());
        map.put("prereq", course.getPrereq());

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("courses").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()){
                            db.collection("courses").document(document.getId()).set(map);
                            Toast.makeText(getApplicationContext(), "Course Updated Successfully",
                                    Toast.LENGTH_LONG).show();
                        }
                        else {
                            Toast.makeText(getApplicationContext(), "Error Updating Course",
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
}
