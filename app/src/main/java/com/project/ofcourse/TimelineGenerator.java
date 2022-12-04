package com.project.ofcourse;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class TimelineGenerator extends AppCompatActivity {
    // idk if you still need this
    DatabaseReference STREF;

    RecyclerView recyclerView;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    TimelineAdapter adapter;
    ArrayList<String> list;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_home);

        // do stuff

        recyclerView = findViewById(R.id.id);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        list = new ArrayList<String>();
        adapter = new TimelineAdapter(this, list);
        recyclerView.setAdapter(adapter);

        eventChangeListener();
    }

    private void eventChangeListener(){
        // you have to change this, im not sure how you're storing the info in firebase
        db.collection("student").orderBy("code", Query.Direction.ASCENDING)
                .addSnapshotListener(new EventListener<QuerySnapshot>(){
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (error != null){
                            Log.e("Firestore error", error.getMessage());
                            return;
                        }
                        for (DocumentChange dc : value.getDocumentChanges()){
                            list.add(dc.getDocument().toObject(String.class));
                        }
                        adapter.notifyDataSetChanged();
                    }
                });
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
