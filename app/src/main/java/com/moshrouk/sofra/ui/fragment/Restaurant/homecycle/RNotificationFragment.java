package com.moshrouk.sofra.ui.fragment.Restaurant.homecycle;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.moshrouk.sofra.R;
import com.moshrouk.sofra.adapter.general.NotificationAdapter;
import com.moshrouk.sofra.data.model.general.notifications.DataNotify;
import com.moshrouk.sofra.data.reset.restaurant.RestaurantApiServices;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.moshrouk.sofra.data.reset.restaurant.RetrofitRestaurant.getRestaurant;

/**
 * A simple {@link Fragment} subclass.
 */
public class RNotificationFragment extends Fragment {


    @BindView(R.id.notify_list)
    RecyclerView notifyList;
    Unbinder unbinder;
    private NotificationAdapter notificationAdapter;
    private LinearLayoutManager linearLayoutManager;
    private List<DataNotify> dataNotifies = new ArrayList<>();
    RestaurantApiServices restaurantApiServices = getRestaurant().create(RestaurantApiServices.class);

    public RNotificationFragment() {
        // Required empty public constructor
    }

    public RNotificationFragment(List<DataNotify> dataNotifies) {
        this.dataNotifies = dataNotifies;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_notification, container, false);
        unbinder = ButterKnife.bind(this, view);

        notifyList.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(getActivity());
        notifyList.setLayoutManager(linearLayoutManager);
        notificationAdapter = new NotificationAdapter(dataNotifies,getActivity());
        notifyList.setAdapter(notificationAdapter);
        notificationAdapter.notifyDataSetChanged();
        return view;
    }

//    private void getNotifyList() {
//        restaurantApiServices.getNotificationsList(LoadData(getActivity(),USER_API_TOKEN))
//                .enqueue(new Callback<Notifications>() {
//                    @Override
//                    public void onResponse(Call<Notifications> call, Response<Notifications> response) {
//                        if (response.body().getStatus().equals(1)) {
//                            dataNotifies.addAll(response.body().getData().getData());
//                            notificationAdapter.notifyDataSetChanged();
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(Call<Notifications> call, Throwable t) {
//
//                    }
//                });
//    }

}
