package com.project.ofcourse;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.ArrayList;

public class EditCourse extends AppCompatActivity {
    EditText editCode;
    RecyclerView recyclerView;
    FirebaseFirestore db;
    MyAdapter myAdapter;
    ArrayList<Course> list;
    static DocumentSnapshot doc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_course);

        Button back = findViewById(R.id.backBTN);
        back.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                openCourseList();
            }
        });

        Button edit = findViewById(R.id.editCourseBTN);
        edit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                editCode = (EditText) findViewById(R.id.courseCode);
                editCourse(view);
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
//
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

    public void openEditDetails(){
        Intent intent = new Intent(this, EditCourseDetails.class);
        startActivity(intent);
    }

    public void editCourse(View view) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        String courseCode = editCode.getText().toString().trim();

        CollectionReference usersRef = db.collection("courses");
        Query query = usersRef.whereEqualTo("code", courseCode);
        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful() && !task.getResult().isEmpty()) {
                    for (DocumentSnapshot document : task.getResult()) {
                        if (document.exists()) {
                            doc = document;
                            openEditDetails();
                        }
                    }
                }
                else {
                    Toast.makeText(getApplicationContext(),"Invalid Course Code", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}