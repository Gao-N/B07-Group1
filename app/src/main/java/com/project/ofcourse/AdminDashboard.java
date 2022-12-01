package com.project.ofcourse;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class AdminDashboard extends AppCompatActivity {
    EditText session;
    Button edit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_dashboard);

        Button addBtn = findViewById(R.id.addCourseButton);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAdminAddCourse();
            }
        });

        Button viewBtn = findViewById(R.id.viewCourseListButton);
        viewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewCourseList();
            }
        });

        Button logout = findViewById(R.id.adminLogout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewAdminLogin();
            }
        });
        session = findViewById(R.id.currentSession);
        edit = findViewById(R.id.btnSession);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String[] arr = session.getText().toString().trim().split(" ");
                String[] sess = {"F", "W", "S"};
                if (session.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Session Cannot be Empty",
                            Toast.LENGTH_LONG).show();
                }
                else if (arr.length > 2 || !(Arrays.asList(sess)).contains(arr[0])) {
                    Toast.makeText(getApplicationContext(), "Invalid Session Format",
                            Toast.LENGTH_LONG).show();
                } else {
                    Map<String, Object> map = new HashMap<>();
                    map.put("currentSession", session.getText().toString());
                    FirebaseDatabase.getInstance().getReference().child("admin")
                            .updateChildren(map)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Toast.makeText(getApplicationContext(), "Session Updated Successfully",
                                            Toast.LENGTH_LONG).show();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(getApplicationContext(), "Error Updating Session",
                                            Toast.LENGTH_LONG).show();
                                }
                            });
                }
            }
        });
    }


    public void openAdminAddCourse(){
        Intent intent = new Intent(this, AdminAddCourse.class);
        startActivity(intent);
    }
    public void viewCourseList(){
        Intent intent = new Intent(this, AdminViewCourseList.class);
        startActivity(intent);
    }

    public void viewAdminLogin(){
        Intent intent = new Intent(this, StudentLogin.class);
        startActivity(intent);
    }
}