package com.project.ofcourse;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;

public class TimelineGenerator extends AppCompatActivity {
    // idk if you still need this
    DatabaseReference STREF;

    RecyclerView recyclerView;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    TimelineAdapter adapter;
    HashMap<String, ArrayList<String>> map;
    String sess;
    ArrayList<String> courses;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_home);

        recyclerView = findViewById(R.id.id);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        map = new HashMap<String, ArrayList<String>>();

        // get student document id
        String email = null;
        db.collection("students").whereEqualTo("email", email)
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful() && !task.getResult().isEmpty()){
                            DocumentSnapshot documentSnapshot = task.getResult().getDocuments().get(0);
                            String docID = documentSnapshot.getId();

                            db.collection("students").document(docID).get()
                                    .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                            if (task.isSuccessful()){
                                                DocumentSnapshot document = task.getResult();
                                                if (document.exists()){
                                                    // loop over sessions
                                                    sess = document.get("session", String.class);
                                                    courses = document.get("courses", ArrayList.class);
                                                    map.put(sess, courses);
                                                }
                                            }
                                        }
                                    });
                        }
                    }
                });
        adapter = new TimelineAdapter(this, map);
        recyclerView.setAdapter(adapter);
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
