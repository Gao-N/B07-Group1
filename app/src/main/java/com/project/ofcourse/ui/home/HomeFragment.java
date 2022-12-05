package com.project.ofcourse.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.project.ofcourse.AdminAddCourse;
import com.project.ofcourse.FirebaseHandler;
import com.project.ofcourse.GeneratingDefinePastCoursesActivity;
import com.project.ofcourse.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {

    String[] lines = FirebaseHandler.courseArray();

    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        /*
        final TextView textView = binding.textHome;
        homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        */


        binding.buttonGenerate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGeneratorFlow();
            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void openGeneratorFlow(){
        Intent intent = new Intent(getActivity(), GeneratingDefinePastCoursesActivity.class);
        intent.putExtra("lines", lines);
        startActivity(intent);
    }
}