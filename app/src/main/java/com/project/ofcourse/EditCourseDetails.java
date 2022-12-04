package com.project.ofcourse;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.HashMap;
import java.util.Map;

public class EditCourseDetails extends AppCompatActivity {
    EditText editCode, editName, editSession, editPrereq;
    Course course;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_course_details);

        DocumentSnapshot document = EditCourse.doc;

        editName = (EditText) findViewById(R.id.courseNameET);
        editCode = (EditText) findViewById(R.id.courseCodeET);
        editSession = (EditText) findViewById(R.id.offeringSessionET);
        editPrereq = (EditText) findViewById(R.id.prerequisiteET);

        displayInfo(document);

        Button edit = findViewById(R.id.editDetailsBTN);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                course = new Course(editName.getText().toString().trim(), editCode.getText().toString().trim(),
                        editSession.getText().toString().trim(), editPrereq.getText().toString().trim());
                updateCourse(document);
            }
        });
        Button back = findViewById(R.id.backButton);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewCourseList();
            }
        });
    }

    public void displayInfo(DocumentSnapshot document){
        Course c = document.toObject(Course.class);
        editName.setText(c.getName());
        editCode.setText(c.getCode());
        editSession.setText(c.getSession());
        editPrereq.setText(c.getPrereq());
    }

    public void updateCourse(DocumentSnapshot document){
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
                            viewDash();

                        }
                        else {
                            Toast.makeText(getApplicationContext(), "Error Updating Course",
                                    Toast.LENGTH_LONG).show();
                            viewCourseList();
                        }
                    }
                });
    }
    public void viewDash(){
        Intent intent = new Intent(this, AdminDashboard.class);
        startActivity(intent);
    }
    public void viewCourseList(){
        Intent intent = new Intent(this, AdminViewCourseList.class);
        startActivity(intent);
    }
}