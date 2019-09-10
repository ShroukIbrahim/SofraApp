package com.moshrouk.sofra.ui.activity.Restaurant;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.moshrouk.sofra.R;
import com.moshrouk.sofra.data.model.general.notifications.DataNotify;
import com.moshrouk.sofra.data.model.general.notifications.Notifications;
import com.moshrouk.sofra.data.reset.restaurant.RestaurantApiServices;
import com.moshrouk.sofra.ui.fragment.Restaurant.homecycle.RCommissionsFragment;
import com.moshrouk.sofra.ui.fragment.Restaurant.homecycle.RHomeFragment;
import com.moshrouk.sofra.ui.fragment.Restaurant.homecycle.RMoreFragment;
import com.moshrouk.sofra.ui.fragment.Restaurant.homecycle.RNotificationFragment;
import com.moshrouk.sofra.ui.fragment.Restaurant.homecycle.ROrderListFragment;
import com.moshrouk.sofra.ui.fragment.Restaurant.homecycle.RProfileFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.moshrouk.sofra.data.reset.restaurant.RetrofitRestaurant.getRestaurant;
import static com.moshrouk.sofra.helper.HelperMethod.replace;
import static com.moshrouk.sofra.helper.SharedPreferencesManger.LoadData;
import static com.moshrouk.sofra.helper.SharedPreferencesManger.USER_API_TOKEN;

public class RestaurantHomeActivity extends AppCompatActivity {


    @BindView(R.id.commission_icon)
    ImageView commissionIcon;
    @BindView(R.id.notification_icon)
    ImageView notificationIcon;
    @BindView(R.id.count_num)
    TextView countNum;


    private List<DataNotify> dataNotifies = new ArrayList<>();
    RestaurantApiServices restaurantApiServices = getRestaurant().create(RestaurantApiServices.class);
    private int count_num;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            switch (item.getItemId()) {
                case R.id.navigation_home:
                    RHomeFragment RHomeFragment = new RHomeFragment();
                    replace(RHomeFragment, getSupportFragmentManager(), R.id.restaurant_home_frame, null, null);

                    return true;
                case R.id.navigation_orderlist:
                    ROrderListFragment rorderListFragment = new ROrderListFragment();
                    replace(rorderListFragment, getSupportFragmentManager(), R.id.restaurant_home_frame, null, null);

                    return true;
                case R.id.navigation_profile:
                    RProfileFragment rprofileFragment = new RProfileFragment();
                    replace(rprofileFragment, getSupportFragmentManager(), R.id.restaurant_home_frame, null, null);

                    return true;
                case R.id.navigation_more:
                    RMoreFragment rmoreFragment = new RMoreFragment();
                    replace(rmoreFragment, getSupportFragmentManager(), R.id.restaurant_home_frame, null, null);

                    return true;
            }
            return false;
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retaruant_home);
        ButterKnife.bind(this);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        RHomeFragment rhomeFragment = new RHomeFragment();
        replace(rhomeFragment, getSupportFragmentManager(), R.id.restaurant_home_frame, null, null);
        getNotifyList();


    }
    private void getNotifyList() {
        restaurantApiServices.getNotificationsList(LoadData(this,USER_API_TOKEN))
                .enqueue(new Callback<Notifications>() {
                    @Override
                    public void onResponse(Call<Notifications> call, Response<Notifications> response) {
                        if (response.body().getStatus().equals(1)) {
                            dataNotifies.addAll(response.body().getData().getData());
                            count_num=dataNotifies.size();
                            if ((count_num != 0 )) {
                                countNum.setVisibility(View.VISIBLE);
                                countNum.setText(""+count_num);
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<Notifications> call, Throwable t) {

                    }
                });
    }


    @OnClick({R.id.commission_icon, R.id.notification_icon})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.commission_icon:
                RCommissionsFragment rCommissionsFragment = new RCommissionsFragment();
                replace(rCommissionsFragment, getSupportFragmentManager(), R.id.restaurant_home_frame, null, null);
                break;
            case R.id.notification_icon:
                RNotificationFragment rNotificationFragment = new RNotificationFragment(dataNotifies);
                replace(rNotificationFragment, getSupportFragmentManager(), R.id.restaurant_home_frame, null, null);
                break;
        }
    }
}
