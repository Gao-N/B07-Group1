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
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class AdminLogin extends AppCompatActivity {

    private ProgressDialog progressDialog;

    public String getEmail(){
        EditText emailText = (EditText)findViewById(R.id.emailAddressEditText);
        String email = emailText.getText().toString();

        if (email.isEmpty()) {
            emailText.setError("Email Cannot be Blank");
        }

        return email;
    }

    public String getPassword(){
        EditText passText = (EditText)findViewById(R.id.passwordEditText);
        String pass = passText.getText().toString();

        if (pass.isEmpty()) {
            passText.setError("Email Cannot be Blank");
        }

        return pass;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_login);

        progressDialog = new ProgressDialog(this);

        Button loginBtn = findViewById(R.id.adminLoginButton);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog.setTitle("Admin Login");
                progressDialog.setMessage("Logging on...");
                progressDialog.setCanceledOnTouchOutside(false);
                progressDialog.show();
                String email = getEmail();
                String pass = getPassword();
                if (email.isEmpty() || pass.isEmpty()) {
                    loginError("Incorrect Email or Password");
                }
                else {
                    login(email, pass, new AdminLoginCallback() {
                        @Override
                        public void adminFound(boolean found) {
                            if (found) {
                                openAdminDashboard();
                            } else {
                                loginError("Incorrect Email or Password");
                            }
                        }
                    });
                }
            }
        });
    }
    public void openAdminDashboard(){
        progressDialog.dismiss();
        Intent intent = new Intent(this, AdminDashboard.class);
        startActivity(intent);
    }

    public void loginError(String message) {
        progressDialog.dismiss();
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    public void login(String email, String pass, AdminLoginCallback callback){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("admin").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            boolean isFound = false;
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                if (document.get("email").equals(email) && document.get("password").equals(pass)) {
                                    isFound = true;
                                }
                            }
                            callback.adminFound(isFound);
                        } else {
                            Log.d("TAG", "Error getting documents: ", task.getException());
                        }
                    }
                });
    }
}