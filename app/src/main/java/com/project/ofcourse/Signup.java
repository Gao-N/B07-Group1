package com.project.ofcourse;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Signup extends AppCompatActivity {
    private Button btnSignup;
    //private Button btnBack;

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
                registerUserMsg();
                backToSignIn();
            }
        });
        Button btnBack = findViewById(R.id.button3);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                backToSignIn();
            }
        });
    }

    private void registerUserMsg() {
        Toast.makeText(getApplicationContext(), "Not Yet Implemented", Toast.LENGTH_SHORT).show();
    }

    private void backToSignIn() {
        //Implement intents here to go back to signin page
        Intent intent = new Intent(this, StudentLogin.class);
        startActivity(intent);
    }
}