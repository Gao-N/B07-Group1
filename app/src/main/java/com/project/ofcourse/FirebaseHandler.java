package com.project.ofcourse;

import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.project.ofcourse.Course;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;

public class FirebaseHandler {
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    // Get all courses and make arraylist of strings of their course codes
    public static ArrayList<String> MakeCourseStringList() {
        return null;
    }

    public static void editStudentPastCourses(ArrayList<String> pastCourses) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("students").document(Student.currentUser)
                .update("past_courses", pastCourses);

        /*db.collection("students").whereEqualTo("email", Student.currentUser)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                        if (task.isSuccessful() && !task.getResult().isEmpty()){
                            DocumentSnapshot documentSnapshot = task.getResult().getDocuments().get(0);
                            Student user = documentSnapshot.toObject(Student.class);


                        }
                    }
                });*/

    }

    //Gets an ArrayList of Course objects from an arraylist of strings of course codes.
    public static void getCoursesfromCodes(ArrayList<String> selectedCourses, ArrayList<Course> output) {
        System.out.println("Searching for documents with the codes: " + selectedCourses);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("courses").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()){
                            for (QueryDocumentSnapshot document: task.getResult()){
                                System.out.println("Found document! Data: " + document.getData());
                                Course course = document.toObject(Course.class);
                                System.out.println("Created a course object! Code: " + course.getCode());
                                if (selectedCourses.contains(course.getCode())) {
                                    output.add(course);
                                    System.out.println("Adding course to list: "+course.getCode());
                                }
                            }
                        }
                    }
                });
    }

    public static void getAllCourses(ArrayList<Course> list) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("courses").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()){
                            for (QueryDocumentSnapshot document: task.getResult()){
                                Course course = document.toObject(Course.class);
                                if (course.getCode() != null) {
                                    list.add(course);
                                    System.out.println("Added another course!");
                                }
                            }
                            System.out.println("Full course list: " + list);
                        }
                    }
                });
        System.out.println("Full course list: " + list);
        for (int i = 0 ; i < list.size() ; i++){
            System.out.println("In all course list: " + list.get(i).getCode());
        }
    }

    public static void getStudentPastCourses(ArrayList<String> pastList) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
            db.collection("students").whereEqualTo("email", Student.currentUser)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()){
                                for(QueryDocumentSnapshot document: task.getResult()) {
                                    Student user = document.toObject(Student.class);
                                    if (user != null) {
                                        for (int i = 0 ; i < user.past_courses.size() ; i++){
                                            pastList.set(i, user.past_courses.get(i));
                                        }
                                    }
                                }

                            }

                        }
                    });
    }


    public static void getSession(String session) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("admin").document("WGcecRzvEcbabR2pcrzP").get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()){
                            DocumentSnapshot doc = task.getResult();
                            if (doc.exists()){
                                session.concat(doc.getString("currentSession"));
                            }
                        }

                    }
                });

    }

    public static void setStudentTimeline(LinkedHashMap<String, String[]> generateTimeline) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("students").document(Student.currentUser)
                .update("timeline", generateTimeline);
    }

    public static boolean isAPastCourse(String code) {
        return false;
    }

    // Gets an arraylist of every course's course code
    public static ArrayList<String> getAllCoursesStrings() {
        ArrayList<String> courseCodes = new ArrayList<>();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("courses").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()){
                            for (QueryDocumentSnapshot document: task.getResult()){
                                Course course = document.toObject(Course.class);
                                if (course.getCode() != null) {
                                    courseCodes.add(course.getCode());
                                    System.out.println("Added course " + course.getCode());
                                    System.out.println("Total courses: " + courseCodes.size());
                                }
                            }
                        }
                        else {

                        }
                    }
                });
        System.out.println("Total courses: " + courseCodes.size());
        return courseCodes;
    }

    public static String[] courseArray (){
        ArrayList<String> list = FirebaseHandler.getAllCoursesStrings();
        String[] arr = new String[list.size()];
        for (int i = 0 ; i < list.size() ; i++){
            arr[i] = list.get(i);
            System.out.println("Added course to array " + list.get(i));
        }

        return arr;
    }
}
