package com.project.ofcourse;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Model {

    public Model() {

    }

    public void isFound(String email, String password, StudentLoginCallback callback) {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d("TAG", "signInWithEmail:success");
                            callback.studentFound(true);
                        }
                        else {
                            Log.w("TAG", "signInWithEmail:failure", task.getException());
                            callback.studentFound(false);
                        }
                    }
                });
    }
}
