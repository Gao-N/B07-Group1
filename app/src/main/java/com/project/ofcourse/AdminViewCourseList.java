package com.project.ofcourse;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class AdminViewCourseList extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_view_courselist);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        ScrollView scrollView = findViewById(R.id.scrollView);

        Button back = findViewById(R.id.backbutton);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openAdminDashboard();
            }
        });

        Button add = findViewById(R.id.addCourse);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openAdminAddCourses();
            }
        });

        Button del = findViewById(R.id.delCourse);
        del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openAdminDeleteCourse();
            }
        });
        db.collection("courses")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()){
                            for (QueryDocumentSnapshot document: task.getResult()){
                                Log.d("TAG", "DocumentSnapshot added with ID: " + document.getId());
                            }
                        }
                        else {
                            Log.d("TAG", "Error getting Course", task.getException());
                        }
                    }
                });
    }

    public void openAdminDashboard() {
        Intent intent = new Intent(this, AdminDashboard.class);
        startActivity(intent);
    }

    public void openAdminAddCourses() {
        Intent intent = new Intent(this, AdminAddCourse.class);
        startActivity(intent);
    }

    public void openAdminDeleteCourse() {
        Intent intent = new Intent(this, AdminDeleteCourse.class);
        startActivity(intent);
    }
}


// reads the courses in firebase
// displays
// CSCB07: Software Design
