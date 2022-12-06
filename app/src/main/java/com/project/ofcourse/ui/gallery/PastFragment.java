package com.project.ofcourse.ui.gallery;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.project.ofcourse.Course;
import com.project.ofcourse.EditPastCoursesActivity;
import com.project.ofcourse.MyAdapter;
import com.project.ofcourse.R;
import com.project.ofcourse.databinding.FragmentGalleryBinding;

import java.util.ArrayList;

public class PastFragment extends Fragment {

    private FragmentGalleryBinding binding;

    RecyclerView recyclerView;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    MyAdapter myAdapter;
    ArrayList<Course> list;
    private Context mContext;
    View root;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        GalleryViewModel galleryViewModel =
                new ViewModelProvider(this).get(GalleryViewModel.class);

        binding = FragmentGalleryBinding.inflate(inflater, container, false);
        root = binding.getRoot();

        generateRecyclerView();


        return root;
    }

    public void generateRecyclerView(){
        recyclerView = root.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));

        list = new ArrayList<Course>();
        myAdapter = new MyAdapter(mContext, list);
        recyclerView.setAdapter(myAdapter);

        eventChangeListener();
    }

    private void eventChangeListener() {
        db.collection("students").whereEqualTo("email", FirebaseAuth.getInstance().getCurrentUser().getEmail()).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            ArrayList<String> pastCourses = (ArrayList<String>)task.getResult().getDocuments().get(0).get("past_courses");
                            for (int i = 0; i < pastCourses.size(); i++) {
                                String courseCode = pastCourses.get(i);
                                db.collection("courses").orderBy("code", Query.Direction.ASCENDING)
                                        .addSnapshotListener(new EventListener<QuerySnapshot>() {
                                            @Override
                                            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                                                if (error != null) {
                                                    Log.e("Firestore error", error.getMessage());
                                                    return;
                                                }

                                                for (DocumentChange dc : value.getDocumentChanges()) {
                                                    if (dc.getDocument().toObject(Course.class).code.equals(courseCode)) {
                                                        list.add(dc.getDocument().toObject(Course.class));
                                                    }
                                                }

                                                myAdapter.notifyDataSetChanged();
                                            }
                                        });
                            }
                        }
                    }
                });
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void EditFcn() {
        Intent nextIntent = new Intent(getActivity(), EditPastCoursesActivity.class);
        startActivity(nextIntent);
    }
}