package com.moshrouk.egyadvtask.ui.activity;


import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.moshrouk.egyadvtask.R;
import com.moshrouk.egyadvtask.helper.HelperMethod;
import com.moshrouk.egyadvtask.ui.fragment.usercyclefragmants.LoginFragment;

public class UserActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        LoginFragment loginFragment = new LoginFragment();
        HelperMethod.replace(loginFragment, getSupportFragmentManager(), R.id.user_cycle_activity, null, null);


    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
