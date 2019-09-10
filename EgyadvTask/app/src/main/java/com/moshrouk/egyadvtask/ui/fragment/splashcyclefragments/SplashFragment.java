package com.moshrouk.egyadvtask.ui.fragment.splashcyclefragments;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.moshrouk.egyadvtask.R;
import com.moshrouk.egyadvtask.ui.activity.UserActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class SplashFragment extends Fragment {


    private static final long SPLASH_DISPLAY_LENGTH = 1600;

    public SplashFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_splash, container, false);

        //handler after post delay to run method
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                Intent openLogin = new Intent(getActivity(), UserActivity.class);
                startActivity(openLogin);
            }
        }, SPLASH_DISPLAY_LENGTH);
        return view;

    }
}