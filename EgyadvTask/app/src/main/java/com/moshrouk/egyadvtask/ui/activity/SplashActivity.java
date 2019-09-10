package com.moshrouk.egyadvtask.ui.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.moshrouk.egyadvtask.R;
import com.moshrouk.egyadvtask.helper.HelperMethod;
import com.moshrouk.egyadvtask.ui.fragment.splashcyclefragments.SplashFragment;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        SplashFragment splashFragment = new SplashFragment();
        HelperMethod.replace(splashFragment, getSupportFragmentManager(), R.id.splash_activity, null, null);


    }

    @Override
    public void onBackPressed() {
        finish();
    }
}

