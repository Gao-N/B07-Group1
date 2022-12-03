package com.project.ofcourse;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;

public class GeneratingDefinePastCoursesActivity<CheckboxClass> extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<CheckboxClass> arrayList = new ArrayList<CheckboxClass>();
    String[] lines = FirebaseHandler.MakeCourseStringList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generating_define_past_courses);
    }
}