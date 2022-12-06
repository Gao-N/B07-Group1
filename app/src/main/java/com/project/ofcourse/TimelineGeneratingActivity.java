package com.project.ofcourse;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class TimelineGeneratingActivity extends AppCompatActivity {
    Map<String, ArrayList<String>> timeline;


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

        setContentView(R.layout.activity_timeline_generating_loading);
        Intent endGenerationIntent = new Intent(this, MainActivity.class);
        ArrayList<Course> wantedCourses = new ArrayList<>();
        ArrayList<String> wantedCoursesCodes = getIntent().getStringArrayListExtra("wantedCourses");
        //FirebaseHandler.getCoursesfromCodes(getIntent().getStringArrayListExtra("wantedCourses"), wantedCourses);
        //System.out.println("Searching for documents with the codes: " + selectedCourses);
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        //Get course objects from wanted courses
        db.collection("courses").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()){
                            for (QueryDocumentSnapshot document: task.getResult()){
                                System.out.println("Found document! Data: " + document.getData());
                                Course course = document.toObject(Course.class);
                                System.out.println("Created a course object! Code: " + course.getCode());
                                if (wantedCoursesCodes.contains(course.getCode())) {
                                    wantedCourses.add(course);
                                    System.out.println("Adding course to list: "+course.getCode());
                                }
                            }

                            //Get list of all courses
                            ArrayList<Course> allCourses = new ArrayList<>();
                            db.collection("courses").get()
                                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                            if (task.isSuccessful()){
                                                for (QueryDocumentSnapshot document: task.getResult()){
                                                    Course course = document.toObject(Course.class);
                                                    if (course.getCode() != null) {
                                                        allCourses.add(course);
                                                        System.out.println("Added another course!");
                                                    }
                                                }
                                                System.out.println("Full course list: " + allCourses);
                                            }

                                            //Get all past courses
                                            db.collection("students").whereEqualTo("email", Student.currentUser)
                                                    .get()
                                                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                        ArrayList<String> pastCourses = new ArrayList<>();
                                                        @Override
                                                        public void onComplete(@NonNull Task<QuerySnapshot> task) {

                                                            if (task.isSuccessful()){
                                                                for(QueryDocumentSnapshot document: task.getResult()) {
                                                                    Student user = document.toObject(Student.class);
                                                                    if (user != null) {
                                                                        pastCourses = user.past_courses;
                                                                    }
                                                                }

                                                            }
                                                            db.collection("admin").document("WGcecRzvEcbabR2pcrzP").get()
                                                                    .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                                        @Override
                                                                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                                            String session = new String();
                                                                            if (task.isSuccessful()){
                                                                                DocumentSnapshot doc = task.getResult();
                                                                                if (doc.exists()){
                                                                                    session = doc.getString("currentSession");
                                                                                }
                                                                                TimelineGenerator Generator = new TimelineGenerator(session, pastCourses, wantedCourses, allCourses);
                                                                                timeline = Generator.generateTimeline();
//                                                                                timeline = new LinkedHashMap<String, ArrayList<String>>();
//                                                                                timeline.put("S 22", pastCourses);
//                                                                                timeline.put("W 23", pastCourses);
                                                                                //for (Map.Entry<String, ArrayList<String>> entry : timeline.entrySet()) {
                                                                                    db.collection("students").document(Student.currentUser)
                                                                                            .update("timeline", timeline).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                                                @Override
                                                                                                public void onComplete(@NonNull Task<Void> task) {
                                                                                                    startActivity(endGenerationIntent);
                                                                                                }
                                                                                            });
                                                                                //}



                                                                            }

                                                                        }
                                                                    });



                                                        }
                                                    });
                                        }
                                    });

                        }
                    }
                });

        System.out.println("Sad");


        //ArrayList<Course> allCourses = new ArrayList<>();
        //FirebaseHandler.getAllCourses(allCourses);
        //ArrayList<String> pastCourses = new ArrayList<>();
        //FirebaseHandler.getStudentPastCourses(pastCourses);
        //String Session = new String();
        //FirebaseHandler.getSession(Session);
//        try {
//            Thread.sleep(5000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

    }

}