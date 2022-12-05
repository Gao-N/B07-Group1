package com.project.ofcourse;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class GeneratingDefineWantedCoursesActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<CheckboxClass> arrayList = new ArrayList<CheckboxClass>();
    ArrayList<String> lines = new ArrayList<>();
    //String[] lines = {"A48","B52"};
    CheckboxAdapter adapter;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generating_define_wanted_courses);

        db.collection("courses").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()){
                            for (QueryDocumentSnapshot document: task.getResult()){
                                Course course = document.toObject(Course.class);
                                if (course.getCode() != null) {
                                    lines.add(course.getCode());
                                }
                            }
                            createUI();

                        }
                        else {

                        }
                    }
                });

        Button NextBtn = findViewById(R.id.buttonNext);
        NextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                afterFcn();
            }
        });
    }

    public void createUI(){
        recyclerView = findViewById(R.id.wantedRecycler);
        adapter = new CheckboxAdapter(this, getData());
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(adapter);
    }

    private ArrayList<CheckboxClass> getData() {
        for (int i = 0; i < lines.size(); i++) {
            arrayList.add(new CheckboxClass(lines.get(i), false));
        }
        return arrayList;
    }

    public void afterFcn() {
        Intent nextIntent = new Intent(this, TimelineGeneratingActivity.class);
        nextIntent.putExtra("wantedCourses", adapter.getSelectedCourses());
        startActivity(nextIntent);
    }
}

