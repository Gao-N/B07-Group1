package com.project.ofcourse;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.project.ofcourse.Course;

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
    }

    //Gets an ArrayList of Course objects from an arraylist of strings of course codes.
    public static ArrayList<Course> getCoursesfromCodes(ArrayList<String> selectedCourses) {
        return null;
    }

    public static ArrayList<Course> getAllCourses() {
    }

    public static ArrayList<String> getStudentPastCourses() {
    }

    public static String getSession() {
    }

    public static void setStudentTimeline(LinkedHashMap<String, String[]> generateTimeline) {
    }
}
