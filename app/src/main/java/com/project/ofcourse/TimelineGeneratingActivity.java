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
        System.out.println("Working: " + getIntent().getStringArrayListExtra("wantedCourses"));
        ArrayList<Course> courses = FirebaseHandler.getCoursesfromCodes(getIntent().getStringArrayListExtra("wantedCourses"));
        for (int i = 0 ; i < courses.size() ; i++){
            System.out.println("Wanted course: " +courses.get(i).getCode());
        }
        //TODO: make a cool GIF loading screen
        setContentView(R.layout.activity_timeline_generating_loading);

        /*ArrayList<Course> wantedCourses = FirebaseHandler.getCoursesfromCodes(getIntent().getStringArrayListExtra("wantedCourses"));
        ArrayList<Course> allCourses = FirebaseHandler.getAllCourses();
        ArrayList<String> pastCourses = FirebaseHandler.getStudentPastCourses();
        String Session = FirebaseHandler.getSession();
        TimelineGenerator Generator = new TimelineGenerator(Session, pastCourses, wantedCourses, allCourses);
        FirebaseHandler.setStudentTimeline(Generator.generateTimeline());
        Intent endGenerationIntent = new Intent(this, MainActivity.class);
        startActivity(endGenerationIntent);*/
    }
}