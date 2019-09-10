package com.moshrouk.egyadvtask.ui.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.moshrouk.egyadvtask.R;
import com.moshrouk.egyadvtask.helper.HelperMethod;
import com.moshrouk.egyadvtask.ui.fragment.homecyclefragments.HomeFragment;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        HomeFragment homeFragment = new HomeFragment();
        HelperMethod.replace(homeFragment, getSupportFragmentManager(), R.id.home_activity, null, null);

    }
}
