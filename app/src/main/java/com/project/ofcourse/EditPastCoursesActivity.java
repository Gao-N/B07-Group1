package com.project.ofcourse;

import static java.lang.Thread.sleep;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.project.ofcourse.databinding.ActivityEditPastCoursesBinding;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class EditPastCoursesActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityEditPastCoursesBinding binding;

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    ArrayList<String> lines = new ArrayList<>();
    //String[] lines = FirebaseHandler.courseArray();;

    RecyclerView recyclerView;
    ArrayList<CheckboxClass> arrayList = new ArrayList<CheckboxClass>();
    //String[] lines = {"A48","B52"};
    CheckboxAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        binding = ActivityEditPastCoursesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar);

        db.collection("courses").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()){
                            for (QueryDocumentSnapshot document: task.getResult()){
                                Course course = document.toObject(Course.class);
                                if (course.getCode() != null) {
                                    lines.add(course.getCode());
                                }
                            }
                            createUI();

                        }
                        else {

                        }
                    }
                });

        //createUI();

    }
    private ArrayList<CheckboxClass> getData() {
        for (int i = 0; i < lines.size(); i++) {
            arrayList.add(new CheckboxClass(lines.get(i), false));
        }
        return arrayList;
    }

    public void createUI(){



        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_edit_past_courses);
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        recyclerView = findViewById(R.id.EditCoursesRecycler);
        adapter = new CheckboxAdapter(this, getData());

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(adapter);

        eventChangeListener();
    }
    private void eventChangeListener(){
        db.collection("courses").orderBy("code", Query.Direction.ASCENDING)
                .addSnapshotListener(new EventListener<QuerySnapshot>(){
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (error != null){
                            Log.e("Firestore error", error.getMessage());
                            return;
                        }

                        for (DocumentChange dc : value.getDocumentChanges()){
                            lines.add(dc.getDocument().toObject(Course.class).getCode());
                        }

                        adapter.notifyDataSetChanged();

                    }
                });
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_edit_past_courses);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }

    public void afterFcn() {
        FirebaseHandler.editStudentPastCourses(adapter.getSelectedCourses());
        Intent nextIntent = new Intent(this, MainActivity.class);
        startActivity(nextIntent);
    }
}