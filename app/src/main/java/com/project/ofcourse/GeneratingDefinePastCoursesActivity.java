package com.project.ofcourse;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

public class GeneratingDefinePastCoursesActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<CheckboxClass> arrayList = new ArrayList<CheckboxClass>();
    //String[] lines = (String[]) FirebaseHandler.MakeCourseStringList().toArray();
    String[] lines = {"A48","B52"};
    CheckboxAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generating_define_past_courses);

        recyclerView = findViewById(R.id.past_courses_recycler_generating);
        adapter = new CheckboxAdapter(this, getData());
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(adapter);

        Button NextBtn = findViewById(R.id.buttonNext);
        NextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                afterFcn();
            }
        });
    }

    private ArrayList<CheckboxClass> getData() {
        for (int i = 0; i < lines.length; i++) {
            arrayList.add(new CheckboxClass(lines[i], false));
        }
        return arrayList;
    }

    public void afterFcn() {
        FirebaseHandler.editStudentPastCourses(adapter.getSelectedCourses());
        Intent nextIntent = new Intent(this, GeneratingDefineWantedCoursesActivity.class);
        startActivity(nextIntent);
    }
}

