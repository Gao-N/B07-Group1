package com.project.ofcourse;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class AdminDeleteCourse extends AppCompatActivity {
    EditText editCode;
    RecyclerView recyclerView;
    FirebaseFirestore db;
    MyAdapter myAdapter;
    ArrayList<Course> list;

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
                openDashboard();
            }
        });
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        db = FirebaseFirestore.getInstance();
        list = new ArrayList<Course>();
        myAdapter = new MyAdapter(this, list);
        recyclerView.setAdapter(myAdapter);

        eventChangeListener();
    }

    private void eventChangeListener(){
        db.collection("courses").orderBy("code", Query.Direction.ASCENDING)
                .addSnapshotListener(new EventListener<QuerySnapshot>(){
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (error != null){
                            Log.e("Firestore error", error.getMessage());
                            return;
                        }

                        for (DocumentChange dc : value.getDocumentChanges()){
                            list.add(dc.getDocument().toObject(Course.class));
                        }

                        myAdapter.notifyDataSetChanged();

                    }
                });
    }

    public void openCourseList(){
        Intent intent = new Intent(this, AdminViewCourseList.class);
        startActivity(intent);
    }

    public void openDashboard(){
        Intent intent = new Intent(this, AdminDashboard.class);
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