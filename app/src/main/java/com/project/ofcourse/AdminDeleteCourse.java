package com.project.ofcourse;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class AdminDeleteCourse extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_delete_courses);

        Button back = findViewById(R.id.adminDeleteCoursesBackButton);
        back.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                openCourseList();
            }
        });

        Button del = findViewById(R.id.adminDeleteCoursesButton);
        del.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                //firebase stuff to delete course
                openCourseList();
            }
        });
    }
    public void openCourseList(){
        Intent intent = new Intent(this, AdminViewCourseList.class);
        startActivity(intent);
    }
}
