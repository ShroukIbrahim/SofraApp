package com.moshrouk.sofra.ui.fragment.Restaurant.homecycle;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.moshrouk.sofra.R;
import com.moshrouk.sofra.adapter.restaurant.RestaurantOfferAdapter;
import com.moshrouk.sofra.data.model.restaurant.restaurantoffers.OffersData;
import com.moshrouk.sofra.data.model.restaurant.restaurantoffers.RestaurantOffers;
import com.moshrouk.sofra.data.reset.restaurant.RestaurantApiServices;
import com.moshrouk.sofra.helper.HelperMethod;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.moshrouk.sofra.data.reset.restaurant.RetrofitRestaurant.getRestaurant;
import static com.moshrouk.sofra.helper.SharedPreferencesManger.LoadData;
import static com.moshrouk.sofra.helper.SharedPreferencesManger.USER_API_TOKEN;

/**
 * A simple {@link Fragment} subclass.
 */
public class ROffersFragment extends Fragment {


    @BindView(R.id.restaurant_offer)
    RecyclerView restaurantOffer;
    @BindView(R.id.add_new_offer)
    Button addNewOffer;

    Unbinder unbinder;

    private RestaurantOfferAdapter restaurantOfferAdapter;
    private LinearLayoutManager linearLayoutManager;
    private List<OffersData> offersData = new ArrayList<>();
    private RestaurantApiServices restaurantApiServices = getRestaurant().create(RestaurantApiServices.class);

    String api_token = "Jptu3JVmDXGpJEaQO9ZrjRg5RuAVCo45OC2AcOKqbVZPmu0ZJPN3T1sm0cWx";

    public ROffersFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_restaurantr_offers, container, false);
        unbinder = ButterKnife.bind(this,view);

        restaurantOffer.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(getActivity());
        restaurantOffer.setLayoutManager(linearLayoutManager);
        restaurantOfferAdapter = new RestaurantOfferAdapter(getActivity(), offersData);
        restaurantOffer.setAdapter(restaurantOfferAdapter);
        getAllOffers();
        return view;
    }

    private void getAllOffers() {
        restaurantApiServices.getAllOffers(LoadData(getActivity(),USER_API_TOKEN),1).enqueue(new Callback<RestaurantOffers>() {
            @Override
            public void onResponse(Call<RestaurantOffers> call, Response<RestaurantOffers> response) {
                if (response.body().getStatus().equals(1)) {
                    offersData.addAll(response.body().getData().getData());
                    restaurantOfferAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<RestaurantOffers> call, Throwable t) {

            }
        });
    }

    @OnClick(R.id.add_new_offer)
    public void onViewClicked() {
        RAddNewOfferFragment addNewOfferFragment = new RAddNewOfferFragment();
        HelperMethod.replace(addNewOfferFragment,getFragmentManager(),R.id.restaurant_home_frame,null,null);
    }
}
