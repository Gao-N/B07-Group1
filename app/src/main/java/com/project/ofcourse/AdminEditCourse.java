package com.project.ofcourse;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class AdminEditCourse extends AppCompatActivity {
//    EditText editCode;
//    RecyclerView recyclerView;
//    FirebaseFirestore db;
//    MyAdapter myAdapter;
//    ArrayList<Course> list;
//    static DocumentSnapshot doc;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
//
//        Button back = findViewById(R.id.backBtn);
//        back.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View view) {
//                openCourseList();
//            }
//        });
//
//        Button edit = findViewById(R.id.editCourseBTN);
//        edit.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View view) {
//                editCode = (EditText) findViewById(R.id.editText);
//                editCourse(view);
//                openEditDetails();
//            }
//        });
//        recyclerView = findViewById(R.id.recyclerView);
//        recyclerView.setHasFixedSize(true);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//
//        db = FirebaseFirestore.getInstance();
//        list = new ArrayList<Course>();
//        myAdapter = new MyAdapter(this, list);
//        recyclerView.setAdapter(myAdapter);
//
//        eventChangeListener();
    }
//
//    private void eventChangeListener(){
//        db.collection("courses").orderBy("code", Query.Direction.ASCENDING)
//                .addSnapshotListener(new EventListener<QuerySnapshot>(){
//                    @Override
//                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
//                        if (error != null){
//                            Log.e("Firestore error", error.getMessage());
//                            return;
//                        }
//
//                        for (DocumentChange dc : value.getDocumentChanges()){
//                            list.add(dc.getDocument().toObject(Course.class));
//                        }
//
//                        myAdapter.notifyDataSetChanged();
//
//                    }
//                });
//    }
//
//    public void openCourseList(){
//        Intent intent = new Intent(this, AdminViewCourseList.class);
//        startActivity(intent);
//    }
//
//    public void openEditDetails(){
//        Intent intent = new Intent(this, AdminEditDetails.class);
//        startActivity(intent);
//    }
//
//    public void editCourse(View view){
//        FirebaseFirestore db = FirebaseFirestore.getInstance();
//        String courseCode = editCode.getText().toString().trim();
//
//        CollectionReference usersRef = db.collection("courses");
//        Query query = usersRef.whereEqualTo("code", courseCode);
//        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                if (task.isSuccessful()) {
//                    for (DocumentSnapshot document : task.getResult()) {
//                        if (document.exists()) {
//                            doc = document;
//                            // want to keep the document in mind so that i can display information
//                        }
//                    }
//                }
//            }
//        });
//
//    }

}

// make it so that the course is deleted from the course list