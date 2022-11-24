package com.project.ofcourse;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Signup extends AppCompatActivity {
    private Button btnSignup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        btnSignup = (Button)findViewById(R.id.btnSignup);
        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final EditText txtFirstName = (EditText)findViewById(R.id.txtFirstName);
                final EditText txtLastName = (EditText)findViewById(R.id.txtLastName);
                final EditText txtEmail = (EditText)findViewById(R.id.txtSignupEmail);
                final EditText txtPass = (EditText)findViewById(R.id.txtSignupPassword);
                String email = txtEmail.getText().toString();
                String password = txtPass.getText().toString();
                String first = txtFirstName.getText().toString();
                String last = txtLastName.getText().toString();
                registerUser();
            }
        });
    }

    private void registerUser() {
        Toast.makeText(getApplicationContext(), "Not Yet Implemented", Toast.LENGTH_SHORT).show();
    }
}