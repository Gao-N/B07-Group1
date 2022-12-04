package com.project.ofcourse;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class AdminViewCourseList extends AppCompatActivity {
    RecyclerView recyclerView;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    MyAdapter myAdapter;
    ArrayList<Course> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_view_courselist);

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        Button back = findViewById(R.id.backbutton);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openAdminDashboard();
            }
        });

        Button add = findViewById(R.id.addCourse);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openAdminAddCourses();
            }
        });

        Button del = findViewById(R.id.delCourse);
        del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openAdminDeleteCourse();
            }
        });

        Button edit = (Button) findViewById(R.id.button2);
        edit.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminViewCourseList.this, MyAdminEditCourse.class);
                startActivity(intent);
            }
        }));

        db.collection("courses")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()){
                            for (QueryDocumentSnapshot document: task.getResult()){
                                Log.d("TAG", "DocumentSnapshot added with ID: " + document.getId());
                            }
                        }
                        else {
                            Log.d("TAG", "Error getting Course", task.getException());
                        }
                    }
                });
        recyclerView = findViewById(R.id.id);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

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

    public void openAdminDashboard() {
        Intent intent = new Intent(this, AdminDashboard.class);
        startActivity(intent);
    }

    public void openAdminAddCourses() {
        Intent intent = new Intent(this, AdminAddCourse.class);
        startActivity(intent);
    }

    public void openAdminDeleteCourse() {
        Intent intent = new Intent(this, AdminDeleteCourse.class);
        startActivity(intent);
    }
    public void openAdminEditCourse(){
        Intent intent = new Intent(this, MyAdminEditCourse.class);
        startActivity(intent);
    }
}


// reads the courses in firebase
// displays
// CSCB07: Software Design
