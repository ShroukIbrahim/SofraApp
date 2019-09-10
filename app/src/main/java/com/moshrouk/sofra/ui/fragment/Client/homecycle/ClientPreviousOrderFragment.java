package com.moshrouk.sofra.ui.fragment.Client.homecycle;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.moshrouk.sofra.R;
import com.moshrouk.sofra.adapter.client.ClientPreviousOrderAdapter;
import com.moshrouk.sofra.data.model.client.clientorders.ClientOrderData;
import com.moshrouk.sofra.data.model.client.clientorders.ClientOrders;
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

/**
 * A simple {@link Fragment} subclass.
 */
public class ClientPreviousOrderFragment extends Fragment {


    @BindView(R.id.client_previous_order)
    RecyclerView clientPreviousOrder;
    Unbinder unbinder;
    private LinearLayoutManager linearLayoutManager;
    private ClientPreviousOrderAdapter clientPreviousOrderAdapter;
    private List<ClientOrderData> clientPreviousOrders = new ArrayList<>();
    private ClientApiServices clientApiServices = getClient().create(ClientApiServices.class);

    public ClientPreviousOrderFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_client_previous_order, container, false);
        unbinder = ButterKnife.bind(this,view);
        clientPreviousOrder.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(getActivity());
        clientPreviousOrder.setLayoutManager(linearLayoutManager);
        clientPreviousOrderAdapter = new ClientPreviousOrderAdapter(getActivity(), clientPreviousOrders);
        clientPreviousOrder.setAdapter(clientPreviousOrderAdapter);
        getPreviousOrder();
        return view;
    }

    private void getPreviousOrder(){
        clientApiServices.getMyOrder("HRbqKFSaq5ZpsOKITYoztpFZNylmzL9elnlAThxZSZ52QWqVBIj8Rdq7RhoB", "completed", 1)
                .enqueue(new Callback<ClientOrders>() {
                    @Override
                    public void onResponse(Call<ClientOrders> call, Response<ClientOrders> response) {
                        if (response.body().getStatus().equals(1)) {
                            clientPreviousOrders.addAll(response.body().getData().getData());
                            clientPreviousOrderAdapter.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onFailure(Call<ClientOrders> call, Throwable t) {

                    }
                });
    }

}
