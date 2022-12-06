package com.project.ofcourse;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;

public class TimelineGenerator extends AppCompatActivity {
    // idk if you still need this
    DatabaseReference STREF;



    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_home);


    }
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
