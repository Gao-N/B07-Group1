package com.project.ofcourse;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.LinkedHashMap;

public class TimelineGeneratingActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline_generating_loading);
        generate();
//        try {
//            Thread.sleep(5000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        Intent endGenerationIntent = new Intent(this, MainActivity.class);
        startActivity(endGenerationIntent);
    }

    protected void generate() {
        ArrayList<Course> wantedCourses = FirebaseHandler.getCoursesfromCodes((getIntent().getStringArrayListExtra("wantedCourses")));
        ArrayList<Course> allCourses = FirebaseHandler.getAllCourses();
        ArrayList<String> pastCourses = FirebaseHandler.getStudentPastCourses();
        String Session = FirebaseHandler.getSession();
        TimelineGenerator Generator = new TimelineGenerator(Session, pastCourses, wantedCourses, allCourses);
        FirebaseHandler.setStudentTimeline(Generator.generateTimeline());
    }
}