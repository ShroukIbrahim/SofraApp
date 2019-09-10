package com.moshrouk.sofra.ui.activity.client;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.moshrouk.sofra.R;
import com.moshrouk.sofra.data.local.AppDatabase;
import com.moshrouk.sofra.data.local.Item;
import com.moshrouk.sofra.data.model.general.notifications.DataNotify;
import com.moshrouk.sofra.data.model.general.notifications.Notifications;
import com.moshrouk.sofra.data.reset.client.ClientApiServices;
import com.moshrouk.sofra.ui.fragment.Client.homecycle.HomeFragment;
import com.moshrouk.sofra.ui.fragment.Client.homecycle.MoreFragment;
import com.moshrouk.sofra.ui.fragment.Client.homecycle.NotificationFragment;
import com.moshrouk.sofra.ui.fragment.Client.homecycle.OrderListFragment;
import com.moshrouk.sofra.ui.fragment.Client.homecycle.ProfileFragment;
import com.moshrouk.sofra.ui.fragment.Client.homecycle.cart.CartListFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.moshrouk.sofra.data.reset.client.RetrofitClient.getClient;
import static com.moshrouk.sofra.helper.HelperMethod.replace;
import static com.moshrouk.sofra.helper.SharedPreferencesManger.CLIENT_API_TOKEN;
import static com.moshrouk.sofra.helper.SharedPreferencesManger.LoadData;

public class ClientHomeActivity extends AppCompatActivity {

    @BindView(R.id.cart_icon)
    ImageView cartIcon;
    @BindView(R.id.notification_icon)
    ImageView notificationIcon;
    @BindView(R.id.count_num)
    TextView countNum;
    Unbinder unbinder;
    @BindView(R.id.count_cart_num)
    TextView countCartNum;
    private Boolean checkLogin = false;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    HomeFragment homeFragment = new HomeFragment();
                    replace(homeFragment, getSupportFragmentManager(), R.id.client_home_frame, null, null);

                    return true;
                case R.id.navigation_orderlist:
                    OrderListFragment orderListFragment = new OrderListFragment();
                    replace(orderListFragment, getSupportFragmentManager(), R.id.client_home_frame, null, null);

                    return true;
                case R.id.navigation_profile:
                    ProfileFragment profileFragment = new ProfileFragment();
                    replace(profileFragment, getSupportFragmentManager(), R.id.client_home_frame, null, null);

                    return true;
                case R.id.navigation_more:
                    MoreFragment moreFragment = new MoreFragment();
                    replace(moreFragment, getSupportFragmentManager(), R.id.client_home_frame, null, null);

                    return true;
            }
            return false;
        }
    };
    private List<Item> items = new ArrayList<>();
    AppDatabase appDatabase ;
     int count_num;
    private List<DataNotify> dataNotifies = new ArrayList<>();
    ClientApiServices clientApiServices = getClient().create(ClientApiServices.class);
    int numCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_home);
        ButterKnife.bind(this);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        HomeFragment homeFragment = new HomeFragment();
        replace(homeFragment, getSupportFragmentManager(), R.id.client_home_frame, null, null);

        appDatabase  = AppDatabase.getAppDatabase(this);
        items.addAll(appDatabase.getItemDAO().getItems());
        count_num=items.size();
        if (count_num!=0) {
            countCartNum.setVisibility(View.VISIBLE);
            countCartNum.setText(""+count_num);
        }

        clientApiServices.getNotificationsList(LoadData(this,CLIENT_API_TOKEN))
                .enqueue(new Callback<Notifications>() {
                    @Override
                    public void onResponse(Call<Notifications> call, Response<Notifications> response) {
                        if (response.body().getStatus().equals(1)) {
                            dataNotifies.addAll(response.body().getData().getData());
                            numCount=dataNotifies.size();
                            if (numCount!=0) {
                                countNum.setVisibility(View.VISIBLE);
                                countNum.setText(""+numCount);
                            }

                        }
                    }

                    @Override
                    public void onFailure(Call<Notifications> call, Throwable t) {

                    }
                });
    }


    @OnClick({R.id.cart_icon, R.id.notification_icon})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.cart_icon:
                CartListFragment cartListFragment = new CartListFragment();
                replace(cartListFragment, getSupportFragmentManager(), R.id.client_home_frame, null, null);
                break;
            case R.id.notification_icon:
                NotificationFragment notificationFragment = new NotificationFragment();
                replace(notificationFragment, getSupportFragmentManager(), R.id.client_home_frame, null, null);

                break;
        }
    }
}
