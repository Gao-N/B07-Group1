package com.project.ofcourse;

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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class AdminLogin extends AppCompatActivity {

    public String getEmail(){
        return ((EditText)findViewById(R.id.emailAddressEditText)).getText().toString();
    }
    public String getPassword(){
        return ((EditText)findViewById(R.id.passwordEditText)).getText().toString();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_login);

        DatabaseReference database = FirebaseDatabase.getInstance().getReference("courses");
        Button loginBtn = findViewById(R.id.adminLoginButton);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //login(view);
                openAdminDashboard();
            }
        });
    }
    public void openAdminDashboard(){
        Intent intent = new Intent(this, AdminDashboard.class);
        startActivity(intent);
    }

    public void login(View view){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        final String[] docID = new String[1];
        // getting the document id for the input email
        db.collection("admin").whereEqualTo("email", getEmail())
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful() && task.getResult().isEmpty()){
                            DocumentSnapshot documentSnapshot = task.getResult().getDocuments().get(0);
                            docID[0] = documentSnapshot.getId();
                        }
                    }
                });
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("admin");
        ref.child(docID[0]).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                // able to get the info
                if (task.isSuccessful()) {
                    // the info is non-null
                    if (task.getResult().exists()){
                        DataSnapshot dataSnapshot = task.getResult();
                        String email = String.valueOf(dataSnapshot.child("email").getValue());
                        String pass = String.valueOf(dataSnapshot.child("password").getValue());

                        // email and password are in database
                        if (email.equals(getEmail()) && pass.equals(getPassword())){
                            openAdminDashboard();
                        }
                        // password doesn't match
                        else if(!pass.equals(getPassword())){
                            Toast.makeText(getApplicationContext(), "Invalid password",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                    else {
                        // null
                        Toast.makeText(getApplicationContext(), "Cannot find admin credentials",
                                Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    // couldn't read info
                    Toast.makeText(getApplicationContext(), "Failed to read",
                            Toast.LENGTH_SHORT).show();

                }
            }
        });
    }
}