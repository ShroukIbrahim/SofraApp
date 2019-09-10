package com.moshrouk.sofra.ui.activity.client;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.moshrouk.sofra.R;
import com.moshrouk.sofra.helper.HelperMethod;
import com.moshrouk.sofra.ui.fragment.Client.clientcycle.LoginFragment;


public class ClientActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        LoginFragment loginFragment = new LoginFragment();
        HelperMethod.replace(loginFragment,getSupportFragmentManager(),R.id.activity_usercycle,null,null);




    }
}
