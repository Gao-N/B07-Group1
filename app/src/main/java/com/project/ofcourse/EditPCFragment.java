package com.project.ofcourse;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.project.ofcourse.databinding.FragmentFirstBinding;

import java.util.ArrayList;

public class EditPCFragment extends Fragment {

    private FragmentFirstBinding binding;
    private Context mContext;
    CheckboxAdapter adapter;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentFirstBinding.inflate(inflater, container, false);
        //CheckboxAdapter = new CheckboxAdapter(mContext, )
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.buttonFirst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                backToMain();            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void backToMain() {
        ArrayList<String> pastCourses = adapter.getSelectedCourses();
        //FirebaseHandler.editStudentPastCourses(adapter.getSelectedCourses());
        Intent nextIntent = new Intent(getActivity(), MainActivity.class);
        startActivity(nextIntent);
    }


}