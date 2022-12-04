package com.project.ofcourse.ui.gallery;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.project.ofcourse.Course;
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
        db.collection("courses").orderBy("code", Query.Direction.ASCENDING)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (error != null) {
                            Log.e("Firestore error", error.getMessage());
                            return;
                        }

                        for (DocumentChange dc : value.getDocumentChanges()) {
                            list.add(dc.getDocument().toObject(Course.class));
                        }

                        myAdapter.notifyDataSetChanged();

                    }
                });
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}