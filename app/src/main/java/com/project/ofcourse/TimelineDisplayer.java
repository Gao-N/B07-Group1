package com.project.ofcourse;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;

public class TimelineDisplayer extends AppCompatActivity {
    RecyclerView recyclerView;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    TimelineAdapter adapter;
    HashMap<String, ArrayList<String>> map;
    String sess;
    ArrayList<String> courses;

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_home);

        recyclerView = findViewById(R.id.id2);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        map = new HashMap<String, ArrayList<String>>();

        String email = FirebaseAuth.getInstance().getCurrentUser().getEmail();
        //Button generate = findViewById(R.id.buttonGenerate);


        adapter = new TimelineAdapter(this, map);
        recyclerView.setAdapter(adapter);

        eventChangeListener();

    }
    public void eventChangeListener() {
        ArrayList<String> arr = new ArrayList<String>();
        arr.add("CSCA08");
        arr.add("MATA67");
        arr.add("MATA31");
        map.put("F22", arr);

        adapter.notifyDataSetChanged();
    }

}
//        db.collection("students").whereEqualTo("email", email)
//                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                        if (task.isSuccessful() && !task.getResult().isEmpty()) {
//                            DocumentSnapshot documentSnapshot = task.getResult().getDocuments().get(0);
//                            String docID = documentSnapshot.getId();
//
//                            db.collection("students").document(docID).get()
//                                    .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//                                        @Override
//                                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//                                            if (task.isSuccessful()){
//                                                DocumentSnapshot document = task.getResult();
//                                                if (document.exists()){
//                                                    // loop over sessions
//                                                    sess = document.get("session", String.class);
//                                                    courses = document.get("courses", ArrayList.class);
//                                                    map.put(sess, courses);
//                                                }
//                                            }
//                                        }
//                                    });
//                        }
//                    }
//                });
