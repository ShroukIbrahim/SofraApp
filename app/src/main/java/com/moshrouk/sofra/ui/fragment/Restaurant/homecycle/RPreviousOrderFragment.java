package com.moshrouk.sofra.ui.fragment.Restaurant.homecycle;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.moshrouk.sofra.R;
import com.moshrouk.sofra.adapter.restaurant.RestaurantPreviousOrder;
import com.moshrouk.sofra.data.model.restaurant.restaurantorder.Order;
import com.moshrouk.sofra.data.model.restaurant.restaurantorder.OrderData;
import com.moshrouk.sofra.data.reset.restaurant.RestaurantApiServices;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.moshrouk.sofra.data.reset.restaurant.RetrofitRestaurant.getRestaurant;

/**
 * A simple {@link Fragment} subclass.
 */
public class RPreviousOrderFragment extends Fragment {


    @BindView(R.id.previous_order_list)
    RecyclerView previousOrderList;
    Unbinder unbinder;

    private RestaurantPreviousOrder previousOrder;
    private LinearLayoutManager linearLayoutManager;
    private List<OrderData> orderData = new ArrayList<>();
    private RestaurantApiServices restaurantApiServices = getRestaurant().create(RestaurantApiServices.class);


    public RPreviousOrderFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_restaurant_previous_order, container, false);
        unbinder = ButterKnife.bind(this,view);
        previousOrderList.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(getActivity());
        previousOrderList.setLayoutManager(linearLayoutManager);
        previousOrder = new RestaurantPreviousOrder(getActivity(), orderData);
        previousOrderList.setAdapter(previousOrder);
        getPreviousOrder();
        return view;
    }

    private void getPreviousOrder() {
        restaurantApiServices.getOrder("Jptu3JVmDXGpJEaQO9ZrjRg5RuAVCo45OC2AcOKqbVZPmu0ZJPN3T1sm0cWx","completed",1)
                .enqueue(new Callback<Order>() {
                    @Override
                    public void onResponse(Call<Order> call, Response<Order> response) {
                        if (response.body().getStatus().equals(1)) {
                            orderData.addAll(response.body().getData().getData());
                            previousOrder.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onFailure(Call<Order> call, Throwable t) {

                    }
                });
    }

}
