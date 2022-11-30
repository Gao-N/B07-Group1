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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import org.jetbrains.annotations.NotNull;

public class AdminDeleteCourse extends AppCompatActivity {
    EditText editCode;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_delete_courses);

        Button back = findViewById(R.id.adminDeleteCoursesBackButton);
        back.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                openCourseList();
            }
        });

        Button del = findViewById(R.id.adminDeleteCoursesButton);
        del.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                //firebase stuff to delete course
                editCode = (EditText) findViewById(R.id.adminDeleteCourseEditText);
                delCourse(view);
                // remove the course from the scrollView
                openCourseList();
            }
        });
    }
    public void openCourseList(){
        Intent intent = new Intent(this, AdminViewCourseList.class);
        startActivity(intent);
    }

    public void delCourse(View view){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        String courseCode = editCode.getText().toString().trim();

        db.collection("courses").whereEqualTo("code", courseCode)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                        if (task.isSuccessful() && !task.getResult().isEmpty()){
                            DocumentSnapshot documentSnapshot = task.getResult().getDocuments().get(0);
                            String docID = documentSnapshot.getId();

                            db.collection("courses").document(docID)
                                    .delete()
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            Toast.makeText(getApplicationContext(), "Successfully Deleted Course", Toast.LENGTH_LONG).show();
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull @NotNull Exception e){
                                            Toast.makeText(getApplicationContext(), "Error, Failed to Delete Course", Toast.LENGTH_LONG).show();

                                        }
                                    });
                        } else {
                            Toast.makeText(getApplicationContext(), "Error, Failed to Delete Course", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

}

// make it so that the course is deleted from the course list