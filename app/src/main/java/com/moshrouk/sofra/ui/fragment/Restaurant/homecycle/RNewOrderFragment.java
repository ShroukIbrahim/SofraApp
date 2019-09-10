package com.moshrouk.sofra.ui.fragment.Restaurant.homecycle;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.moshrouk.sofra.R;
import com.moshrouk.sofra.adapter.restaurant.RestaurantNewOrder;
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
public class RNewOrderFragment extends Fragment {


    @BindView(R.id.new_order_list)
    RecyclerView newOrderList;

    Unbinder unbinder;

    private RestaurantNewOrder restaurantNewOrderAdapter;
    private LinearLayoutManager linearLayoutManager;
    private List<OrderData> orderData = new ArrayList<>();
    private RestaurantApiServices restaurantApiServices = getRestaurant().create(RestaurantApiServices.class);

    public RNewOrderFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_restaurant_new_order, container, false);
        unbinder = ButterKnife.bind(this, view);

        newOrderList.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(getActivity());
        newOrderList.setLayoutManager(linearLayoutManager);
        restaurantNewOrderAdapter = new RestaurantNewOrder(getActivity(), orderData);
        newOrderList.setAdapter(restaurantNewOrderAdapter);
        getNewOrder();
        return view;
    }

    private void getNewOrder() {
        restaurantApiServices.getOrder("Jptu3JVmDXGpJEaQO9ZrjRg5RuAVCo45OC2AcOKqbVZPmu0ZJPN3T1sm0cWx","pending",1)
                .enqueue(new Callback<Order>() {
                    @Override
                    public void onResponse(Call<Order> call, Response<Order> response) {
                        if (response.body().getStatus().equals(1)) {
                            orderData.addAll(response.body().getData().getData());
                            restaurantNewOrderAdapter.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onFailure(Call<Order> call, Throwable t) {

                    }
                });
    }

}
