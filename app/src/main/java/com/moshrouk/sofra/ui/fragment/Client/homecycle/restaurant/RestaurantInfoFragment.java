package com.moshrouk.sofra.ui.fragment.Client.homecycle.restaurant;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.moshrouk.sofra.R;
import com.moshrouk.sofra.data.model.general.restaurantdetails.RestaurantDetails;
import com.moshrouk.sofra.data.reset.general.GeneralApiServices;

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
public class RestaurantInfoFragment extends Fragment {


    @BindView(R.id.stats)
    TextView stats;
    @BindView(R.id.city)
    TextView city;
    @BindView(R.id.region)
    TextView region;
    @BindView(R.id.minimum_charger)
    TextView minimumCharger;
    @BindView(R.id.delivery_cost)
    TextView deliveryCost;
    Unbinder unbinder;
    private int restaurantId;
    private GeneralApiServices generalApiServices = getGeneral().create(GeneralApiServices.class);


    public RestaurantInfoFragment() {
        // Required empty public constructor
    }

    public RestaurantInfoFragment(int restaurantId) {
        this.restaurantId = restaurantId;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_restaurant_info, container, false);
        unbinder = ButterKnife.bind(this,view);
        getRestaurantsDetails();
        return view;
    }

    private void getRestaurantsDetails(){
        generalApiServices.getRestaurantsDetails(restaurantId)
                .enqueue(new Callback<RestaurantDetails>() {
                    @Override
                    public void onResponse(Call<RestaurantDetails> call, Response<RestaurantDetails> response) {
                        if (response.body().getStatus().equals(1)) {
                            stats.setText(response.body().getData().getAvailability());
                           city.setText(response.body().getData().getRegion().getCity().getName());
                            region.setText(response.body().getData().getRegion().getName());
                            minimumCharger.setText(response.body().getData().getMinimumCharger());
                            deliveryCost.setText(response.body().getData().getDeliveryCost());


                        }
                    }

                    @Override
                    public void onFailure(Call<RestaurantDetails> call, Throwable t) {

                    }
                });
    }
}
