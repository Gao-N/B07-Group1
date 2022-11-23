package com.project.ofcourse;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class AdminDeleteCourse extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_delete_courses);

        Button back = (Button)findViewById(R.id.adminDeleteCoursesBackButton);
        back.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                setContentView(R.layout.admin_view_courselist);
            }
        });
    }
}

/*

public class AdminLogin extends AppCompatActivity {
// make the button go to the next activity
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button loginBtn = (Button)findViewById(R.id.adminLoginButton);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //startActivity(new Intent(MainActivity.this, AdminDashboard.class));
                // have to create a Dashboard Activity in manifest and a Dashboard class like this
            }
        });

    }
}
 */