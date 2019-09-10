package com.moshrouk.sofra.ui.fragment.Client.homecycle.restaurant;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.moshrouk.sofra.R;
import com.moshrouk.sofra.adapter.client.RestaurantItemListAdapter;
import com.moshrouk.sofra.data.model.general.restaurant_items.Items;
import com.moshrouk.sofra.data.model.general.restaurant_items.ItemsData;
import com.moshrouk.sofra.data.reset.general.GeneralApiServices;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.moshrouk.sofra.data.reset.general.RetrofitGeneral.getGeneral;

/**
 * A simple {@link Fragment} subclass.
 */
public class FoodMenuFragment extends Fragment {
     int restaurantId;
    @BindView(R.id.restaurant_items)
    RecyclerView restaurantItems;
    Unbinder unbinder;

    private RestaurantItemListAdapter restaurantItemListAdapter;
    private LinearLayoutManager linearLayoutManager;
    private List<ItemsData> itemsData = new ArrayList<>();
    private GeneralApiServices generalApiServices = getGeneral().create(GeneralApiServices.class);



    public FoodMenuFragment(int restaurantId) {

        this.restaurantId = restaurantId;
    }

    public FoodMenuFragment() {
        // Required empty public constructor

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_food_menu, container, false);
        unbinder = ButterKnife.bind(this,view);
        restaurantItems.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(getActivity());
        restaurantItems.setLayoutManager(linearLayoutManager);
        restaurantItemListAdapter = new RestaurantItemListAdapter(getActivity(), itemsData);
        restaurantItems.setAdapter(restaurantItemListAdapter);
        getRestaurantItems();
        return view;
    }

    private void getRestaurantItems() {
        generalApiServices.getAllItems(restaurantId).enqueue(new Callback<Items>() {
            @Override
            public void onResponse(Call<Items> call, Response<Items> response) {
                if (response.body().getStatus().equals(1)) {
                    itemsData.addAll(response.body().getData().getData());
                    restaurantItemListAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<Items> call, Throwable t) {

            }
        });
    }


}
