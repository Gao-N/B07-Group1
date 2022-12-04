package com.project.ofcourse;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.project.ofcourse.Course;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;

public class FirebaseHandler {
    FirebaseFirestore db;

    // Get all courses and make arraylist of strings of their course codes
    public static ArrayList<String> MakeCourseStringList() {
        return null;
    }

    public static void editStudentPastCourses(ArrayList<String> pastCourses) {
    }

    //Gets an ArrayList of Course objects from an arraylist of strings of course codes.
    public static ArrayList<Course> getCoursesfromCodes(ArrayList<String> selectedCourses) {

        return null;
    }

    public static ArrayList<Course> getAllCourses() {

        return null;
    }

    public static ArrayList<String> getStudentPastCourses() {

        return null;
    }

    public static String getSession() {
        return null;
    }

    public static void setStudentTimeline(LinkedHashMap<String, String[]> generateTimeline) {

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
