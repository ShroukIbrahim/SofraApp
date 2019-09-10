package com.moshrouk.sofra.ui.fragment.Client.homecycle;


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
import com.moshrouk.sofra.data.model.general.notifications.Notifications;
import com.moshrouk.sofra.data.reset.client.ClientApiServices;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.moshrouk.sofra.data.reset.client.RetrofitClient.getClient;
import static com.moshrouk.sofra.helper.SharedPreferencesManger.CLIENT_API_TOKEN;
import static com.moshrouk.sofra.helper.SharedPreferencesManger.LoadData;

/**
 * A simple {@link Fragment} subclass.
 */
public class NotificationFragment extends Fragment {


    @BindView(R.id.notify_list)
    RecyclerView notifyList;
    Unbinder unbinder;

    private NotificationAdapter notificationAdapter;
    private LinearLayoutManager linearLayoutManager;
    private List<DataNotify> dataNotifies = new ArrayList<>();
    ClientApiServices clientApiServices = getClient().create(ClientApiServices.class);
    public static int numCount;


    public NotificationFragment() {
        // Required empty public constructor
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
        getNotifyList();
        return view;
    }

    private void getNotifyList() {
        clientApiServices.getNotificationsList(LoadData(getActivity(),CLIENT_API_TOKEN))
                .enqueue(new Callback<Notifications>() {
                    @Override
                    public void onResponse(Call<Notifications> call, Response<Notifications> response) {
                        if (response.body().getStatus().equals(1)) {
                            dataNotifies.addAll(response.body().getData().getData());
                            numCount=dataNotifies.size();
                            notificationAdapter.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onFailure(Call<Notifications> call, Throwable t) {

                    }
                });
    }

}
