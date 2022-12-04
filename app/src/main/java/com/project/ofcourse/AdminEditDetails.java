package com.project.ofcourse;

//public class AdminEditDetails extends AppCompatActivity{
//    EditText editCode, editName, editSession, editPrereq;
//    Course course;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState){
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.admin_edit_course_details);
//
//        DocumentSnapshot document = AdminEditCourse.doc;
//        editCode = (EditText) findViewById(R.id.courseCodeEditText);
//        editName = (EditText) findViewById(R.id.courseNameEditText);
//        editSession = (EditText) findViewById(R.id.offeringSessionEditText);
//        editPrereq = (EditText) findViewById(R.id.prerequisiteEditText);
//        displayInfo(document);
//
//        Button edit = findViewById(R.id.editDetailsBTN);
//        edit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                course = new Course(editName.toString().trim(), editCode.toString().trim(),
//                        editSession.toString().trim(), editPrereq.toString().trim());
//                updateCourse(document);
//            }
//        });
//    }
//
//    public void displayInfo(DocumentSnapshot document){
//        Course c = document.toObject(Course.class);
//        editName.setText(c.getName());
//        editCode.setText(c.getCode());
//        editSession.setText(c.getSession());
//        editPrereq.setText(c.getPrereq());
//    }
//
//    public void updateCourse(DocumentSnapshot document){
//        if (course.getName().isEmpty()){
//            editName.setError("Course name is required");
//            editName.requestFocus();
//            return;
//        }
//        if (course.getCode().isEmpty()){
//            editCode.setError("Course code is required");
//            editCode.requestFocus();
//            return;
//        }
//        if (course.getSession().isEmpty()){
//            editSession.setError("Course session is required");
//            editSession.requestFocus();
//            return;
//        }
//
//        Map<String, Object> map = new HashMap<>();
//        map.put("name", course.getName());
//        map.put("code", course.getCode());
//        map.put("session", course.getSession());
//        map.put("prereq", course.getPrereq());
//
//        FirebaseFirestore db = FirebaseFirestore.getInstance();
//        db.collection("courses").get()
//                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                        if (task.isSuccessful()){
//                            db.collection("courses").document(document.getId()).set(map);
//                            Toast.makeText(getApplicationContext(), "Course Updated Successfully",
//                                    Toast.LENGTH_LONG).show();
//                        }
//                        else {
//                            Toast.makeText(getApplicationContext(), "Error Updating Course",
//                                    Toast.LENGTH_LONG).show();
//                        }
//                    }
//                });
//    }
//}