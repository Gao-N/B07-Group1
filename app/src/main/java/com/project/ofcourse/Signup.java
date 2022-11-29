package com.project.ofcourse;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Signup extends AppCompatActivity {
    private Button btnSignup;
    //private Button btnBack;
    EditText inputEmail, inputPassword;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    FirebaseAuth mAuth;
    FirebaseUser mUser;
    ProgressDialog progressD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        inputEmail = findViewById(R.id.txtSignupEmail);
        inputPassword = findViewById(R.id.txtSignupPassword);
        progressD = new ProgressDialog(this);
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        btnSignup = (Button)findViewById(R.id.btnSignup);
        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final EditText txtFirstName = (EditText)findViewById(R.id.txtFirstName);
                final EditText txtLastName = (EditText)findViewById(R.id.txtLastName);
                String first = txtFirstName.getText().toString();
                String last = txtLastName.getText().toString();
                authenticateUser();
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

    private void authenticateUser() {
        String authEmail = inputEmail.getText().toString();
        String authPassword = inputPassword.getText().toString();

        if (!authEmail.matches(emailPattern)) {
            inputEmail.setError("Enter valid/correct Email");
        } else if (authPassword.isEmpty() || authPassword.length() < 6) {
            inputPassword.setError("Enter Password");
        } else {
            progressD.setMessage("Registering User...");
            progressD.setTitle("Registration");
            progressD.setCanceledOnTouchOutside(false);
            progressD.show();

            mAuth.createUserWithEmailAndPassword(authEmail, authPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        progressD.dismiss();
                        backToSignIn();
                        Toast.makeText(Signup.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                    } else {
                        progressD.dismiss();
                        Toast.makeText(Signup.this, "" + task.getException(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    private void backToSignIn() {
        //Implement intents here to go back to sign-in page
        Intent intent = new Intent(this, StudentLogin.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}