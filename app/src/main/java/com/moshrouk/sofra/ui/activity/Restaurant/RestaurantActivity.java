package com.moshrouk.sofra.ui.activity.Restaurant;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.moshrouk.sofra.R;
import com.moshrouk.sofra.helper.HelperMethod;
import com.moshrouk.sofra.ui.fragment.Restaurant.restaurantcycle.LoginFragment;


public class RestaurantActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant);

        LoginFragment loginFragment = new LoginFragment();
        HelperMethod.replace(loginFragment,getSupportFragmentManager(),R.id.resturant_cycle,null,null);



    }

}
