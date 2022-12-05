package com.project.ofcourse;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Signup extends AppCompatActivity {
    private Button btnSignup;
    //private Button btnBack;
    EditText inputEmail, inputPassword;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    FirebaseAuth mAuth;
    FirebaseUser mUser;
    FirebaseFirestore mStore;
    EditText txtFirstName, txtLastName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        inputEmail = (EditText)findViewById(R.id.txtSignupEmail);
        inputPassword = (EditText)findViewById(R.id.txtSignupPassword);
        txtFirstName = (EditText)findViewById(R.id.txtFirstName);
        txtLastName = (EditText) findViewById(R.id.txtLastName);


        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        mStore = FirebaseFirestore.getInstance();

        btnSignup = findViewById(R.id.btnSignup);

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                authenticateUser(view);
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

    public void authenticateUser(View view) {
        String authEmail = inputEmail.getText().toString().trim();
        String authPassword = inputPassword.getText().toString().trim();
        String first = txtFirstName.getText().toString().trim();
        String last = txtLastName.getText().toString().trim();

        if (!authEmail.matches(emailPattern)) {
            inputEmail.setError("Enter valid/correct Email");
        } else if (authPassword.isEmpty() || authPassword.length() < 6) {
            inputPassword.setError("Enter Password");
        } else {
            mAuth.createUserWithEmailAndPassword(authEmail, authPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        Student studentID = new Student(authEmail, first, last);
                        Map studentHashMap = new HashMap<>();
                        studentHashMap.put("name", studentID.name);
                        studentHashMap.put("email", studentID.email);
                        studentHashMap.put("past_courses", studentID.past_courses);
                        studentHashMap.put("timeline", studentID.timeline);

                        mStore.collection("students")
                                .document(authEmail).set(studentHashMap)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Snackbar.make(view, "User Successfully Added", Snackbar.LENGTH_LONG)
                                                    .setAction("Action", null).show();
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Snackbar.make(view, "Error: User not Added", Snackbar.LENGTH_LONG)
                                                    .setAction("Action", null).show();
                                            Log.w("TAG", "Error adding document", e);
                                        }
                                });
                        backToSignIn();
                        Toast.makeText(Signup.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(Signup.this, "Error: User already exists", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    public void backToSignIn() {
        //Implement intents here to go back to sign-in page
        Intent intent = new Intent(this, StudentLogin.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}