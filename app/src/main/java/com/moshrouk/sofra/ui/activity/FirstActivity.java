package com.moshrouk.sofra.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.moshrouk.sofra.R;
import com.moshrouk.sofra.ui.activity.Restaurant.RestaurantActivity;
import com.moshrouk.sofra.ui.activity.client.ClientHomeActivity;

import butterknife.ButterKnife;


public class FirstActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
        ButterKnife.bind(this);
        Button startActivityUser=(Button) findViewById(R.id.start_activity_client);
        startActivityUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent client = new Intent(FirstActivity.this, ClientHomeActivity.class);
                        startActivity(client);

            }
        });
        Button startActivityRestaurant=(Button) findViewById(R.id.start_activity_restaurant);
        startActivityRestaurant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent Restuarant = new Intent(FirstActivity.this, RestaurantActivity.class);
                        startActivity(Restuarant);

            }
        });

    }




//    @OnClick({R.id.start_activity_seller, R.id.start_activity_order})
//    public void onViewClicked(View view) {
//        switch (view.getId()) {
//            case R.id.start_activity_seller:
//                startActivityRestaurant.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        Intent Restuarant = new Intent(FirstActivity.this, RestaurantHomeActivity.class);
//                        startActivity(Restuarant);
//
//                    }
//                });
//                break;
//            case R.id.start_activity_order:
//                startActivityUser.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        Intent User = new Intent(FirstActivity.this, RestaurantHomeActivity.class);
//                        startActivity(User);
//                    }
//                });
//                break;
//        }
//    }
}
