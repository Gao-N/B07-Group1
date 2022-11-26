package com.project.ofcourse.ui.slideshow;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;


import com.project.ofcourse.MainActivity;
import com.project.ofcourse.R;
import com.project.ofcourse.StudentLogin;
import com.project.ofcourse.databinding.FragmentSlideshowBinding;
import com.project.ofcourse.ui.home.HomeFragment;
import com.project.ofcourse.ui.login.LoginActivity;



public class LogOutFragment extends Fragment {
    private FragmentSlideshowBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        SlideshowViewModel slideshowViewModel =
                new ViewModelProvider(this).get(SlideshowViewModel.class);

        binding = FragmentSlideshowBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        return root;
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.noLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment HomeFrag = new HomeFragment();
                FragmentTransaction ft = getFragmentManager().beginTransaction(); //deprecated but oh my god switching within fragments is annoying
                ft.replace(((ViewGroup)getView().getParent()).getId(), HomeFrag);
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                ft.addToBackStack(null);
                ft.commit();
            }
        });
        binding.yesLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logOutFcn();
            }
        });
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void logOutFcn() {
        Intent LogoutIntent = new Intent(getActivity(), StudentLogin.class);
        startActivity(LogoutIntent);
    }
}