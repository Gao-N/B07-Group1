package com.project.ofcourse;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class StudentLogin extends AppCompatActivity {
    private Presenter presenter;

    private ProgressDialog progressDialog;

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
        progressDialog.dismiss();
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_login);

        progressDialog = new ProgressDialog(this);
        presenter = new Presenter(new Model(), this);

        Button btnLogin = (Button)findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog.setMessage("Logging on...");
                progressDialog.setTitle("Student Login");
                progressDialog.setCanceledOnTouchOutside(false);
                progressDialog.show();
                presenter.checkStudent();
            }
        });

        Button btnSignUp = (Button)findViewById(R.id.btnSignUp);
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openSignUpActivity();
            }
        });

        Button btnAdminLogin = (Button)findViewById(R.id.btnAdminLogin);
        btnAdminLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openAdminLoginActivity();
            }
        });
    }

    public void openTimelineActivity() {
        progressDialog.dismiss();
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