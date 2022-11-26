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

    private Button btnLogin;
    private Button btnSignUp;
    private Button btnAdminLogin;

    public String getEmail() {
        EditText txtEmail = (EditText)findViewById(R.id.txtStudentEmail);
        String email = txtEmail.getText().toString();

        if (email.isEmpty()) {
            txtEmail.setError("Email Cannot be Blank");
        }

        return email;
    }

    public String getPassword() {
        EditText txtPass = (EditText)findViewById(R.id.txtStudentPassword);
        String pass = txtPass.getText().toString();

        if (pass.isEmpty()) {
            txtPass.setError("Password Cannot be Blank");
        }

        return pass;
    }

    public void displayMessage(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_login);

        presenter = new Presenter(new Model(), this);

        btnLogin = (Button)findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.checkStudent();
            }
        });

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

    public void openTimelineActivity() {
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