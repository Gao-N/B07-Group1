package com.project.ofcourse;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class AdminViewCourseList extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_view_courselist);

        Button back = findViewById(R.id.backbutton);
        back.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
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
    }
    public void openAdminDashboard(){
        Intent intent = new Intent(this, AdminDashboard.class);
        startActivity(intent);
    }
    public void openAdminAddCourses(){
        Intent intent = new Intent(this, AdminAddCourse.class);
        startActivity(intent);
    }
    public void openAdminDeleteCourse(){
        Intent intent = new Intent(this, AdminDeleteCourse.class);
        startActivity(intent);
    }
}
