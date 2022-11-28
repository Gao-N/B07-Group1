package com.project.ofcourse;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.database.DatabaseReference;

public class TimelineGenerator {
    DatabaseReference STREF;

    {
        //STREF = new DatabaseReference.CompletionListener();
    }

    /*Will be called in Timeline Generation Activity which consists of:
        - Past Courses Fragment (Creates field for given student in Firebase)
        - Wanted Courses Fragment (Gets stored as an array to be passed here)
        - Generating fragment (Calls this class's main function)
        Which then creates a collection as a field in the student's Firebase from the above info

     */

    //Gets Current Session from Admin's Firebase:


    //Gets Past Courses needed from Student's Past Courses field on Firebase:



    //Gets Wanted Courses from Choose Courses page during Timeline Generation


    //Generates the Timeline:
    public void generateTimeline(String currentsession, String [] wantedcourses, String [] pastcourses) {

    }
}
