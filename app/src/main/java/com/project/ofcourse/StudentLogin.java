package com.project.ofcourse;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class StudentLogin extends AppCompatActivity {
    private Presenter presenter;
    private Student student;

    private Button btnLogin;
    private Button btnSignUp;
    private Button btnAdminLogin;

    private String getEmail() {
        EditText txtEmail = (EditText)findViewById(R.id.txtStudentEmail);
        return txtEmail.getText().toString();
    }

    private String getPassword() {
        EditText txtPass = (EditText)findViewById(R.id.txtStudentPassword);
        return txtPass.getText().toString();
    }

    public void handleClick(View view){}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_login);

//        btnLogin = (Button)findViewById(R.id.btnLogin);
//        btnLogin.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                final EditText txtEmail = (EditText)findViewById(R.id.txtStudentEmail);
//                final EditText txtPass = (EditText)findViewById(R.id.txtStudentPassword);
//                String email = txtEmail.getText().toString();
//                String password = txtPass.getText().toString();
//
//                Student loggedInStudent = new Student(email, password);
//                if (loggedInStudent.login() == 1) {
//                    // loggedInStudent needs to be checked against database before proceeding
//                    openTimelineActivity();
//                }
//                else {
//                    Toast.makeText(getApplicationContext(), "Incorrect Email or Password", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });

        btnSignUp = (Button)findViewById(R.id.btnSignUp);
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openSignUpActivity();
            }
        });

        btnAdminLogin = (Button)findViewById(R.id.btnAdminLogin);
        btnAdminLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openAdminLoginActivity();
            }
        });
    }


    private void openTimelineActivity() {
        // launches the default MainActivity, needs to be changed to the Timeline
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private void openSignUpActivity() {
        Intent intent = new Intent (this, Signup.class);
        startActivity(intent);
    }

    private void openAdminLoginActivity() {
        Intent intent = new Intent (this, AdminLogin.class);
        startActivity(intent);
    }
}