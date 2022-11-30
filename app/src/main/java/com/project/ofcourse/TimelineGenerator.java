package com.project.ofcourse;

import static android.content.ContentValues.TAG;

import android.util.Log;
import android.util.Pair;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;

public class TimelineGenerator {

    private String Session;
    private ArrayList<String> pastCourses;
    private ArrayList<Course> wantedCourses;
    private ArrayList<Course> allCourses;

    /*Will be called in Timeline Generation Activity which consists of:
        - Past Courses Fragment (Creates field for given student in Firebase)
        - Wanted Courses Fragment (Gets stored as an array to be passed here)
        - Generating fragment (Calls this class's main function)
        Which then creates a collection as a field in the student's Firebase from the above info
     */
    public TimelineGenerator(String Session, ArrayList<String> pastCourses, ArrayList<Course> wantedCourses) {


    }


    //Generates the Timeline:
    public void generateTimeline() {
        LinkedHashSet<Pair<String, ArrayList<String>>> Timeline = new LinkedHashSet<>();
        String [] preReqs;
        String presentSession = Session;
        String [] CoursesArray = {};
        Pair SessionPair;
        int coursesInSession = 0;
        for (int i = 0; i < wantedCourses.size(); i++) {
            if (presentSession == Session || coursesInSession >= 5) { // Max 5 courses per session
                SessionPair = new Pair(presentSession, CoursesArray);
                Timeline.add(SessionPair);
                presentSession = advanceSession(presentSession);
                CoursesArray = new String[6];
            }

            preReqs = wantedCourses.get(i).prereq.split(" ");

            for (int j = 0; j < preReqs.length; j++) {          // Check Prerequisite Courses
                if (!pastCourses.contains(preReqs[j])) {        // TODO: A prerequisite is not fulfilled
                    //recursive helper?
                }
            }

            CoursesArray[coursesInSession] = wantedCourses.get(i).code; // Prereqs have been satisfied, add it to the session and move on
            coursesInSession++;


        }

    }

    private String advanceSession(String presentSession) {
    //TODO: Session index handling
        return presentSession;
    }
}
