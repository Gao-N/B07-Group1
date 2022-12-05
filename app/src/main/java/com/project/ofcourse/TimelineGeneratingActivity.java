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
        /* Test Stuff
        ArrayList<Course> allCourses = new ArrayList<>();
        FirebaseHandler.getAllCourses(allCourses);
        System.out.println("Working: " + getIntent().getStringArrayListExtra("wantedCourses"));
        ArrayList<Course> courses = new ArrayList<>();
        FirebaseHandler.getCoursesfromCodes(getIntent().getStringArrayListExtra("wantedCourses"), courses);
        for (int i = 0 ; i < courses.size() ; i++){
            System.out.println("Wanted course: " +courses.get(i).getCode());
        }*/
        //TODO: make a cool GIF loading screen
        setContentView(R.layout.activity_timeline_generating_loading);

        ArrayList<Course> wantedCourses = new ArrayList<>();
        FirebaseHandler.getCoursesfromCodes(getIntent().getStringArrayListExtra("wantedCourses"), wantedCourses);
        ArrayList<Course> allCourses = new ArrayList<>();
        FirebaseHandler.getAllCourses(allCourses);
        ArrayList<String> pastCourses = new ArrayList<>();
        FirebaseHandler.getStudentPastCourses(pastCourses);
        String Session = new String();
        FirebaseHandler.getSession(Session);
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