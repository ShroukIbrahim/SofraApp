package com.moshrouk.sofra.ui.fragment.Client.homecycle;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;
import com.moshrouk.sofra.R;
import com.moshrouk.sofra.adapter.client.RestaurantListAdapter;
import com.moshrouk.sofra.data.model.general.restaurants_list.RestaurantsList;
import com.moshrouk.sofra.data.model.general.restaurants_list.RestaurantsListData;
import com.moshrouk.sofra.data.model.general.cities.Cities;
import com.moshrouk.sofra.data.model.general.cities.CitiesData;
import com.moshrouk.sofra.data.reset.general.GeneralApiServices;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.moshrouk.sofra.data.reset.general.RetrofitGeneral.getGeneral;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {


    @BindView(R.id.restaurant_city_filter)
    Spinner restaurantCityFilter;

    @BindView(R.id.restaurant_list)
    RecyclerView restaurantList;
    Unbinder unbinder;
    @BindView(R.id.restaurant_name)
    TextInputEditText restaurantName;
    @BindView(R.id.search)
    ImageView search;
    private RestaurantListAdapter restaurantListAdapter;
    private LinearLayoutManager linearLayoutManager;
    private List<RestaurantsListData> restaurantsListData = new ArrayList<>();
    private GeneralApiServices generalApiServices = getGeneral().create(GeneralApiServices.class);
    private int city_id;
    String Keyword;


    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_client_home, container, false);
        unbinder = ButterKnife.bind(this, view);

        restaurantList.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(getActivity());
        restaurantList.setLayoutManager(linearLayoutManager);
        restaurantListAdapter = new RestaurantListAdapter(getActivity(), restaurantsListData);
        restaurantList.setAdapter(restaurantListAdapter);

        getCity();
        getAllRestaurants();

        return view;
    }

    private void getAllRestaurants() {
        generalApiServices.getAllRestaurants().enqueue(new Callback<RestaurantsList>() {
            @Override
            public void onResponse(Call<RestaurantsList> call, Response<RestaurantsList> response) {
                if (response.body().getStatus().equals(1)) {
                    restaurantsListData.addAll(response.body().getData().getData());
                    restaurantListAdapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onFailure(Call<RestaurantsList> call, Throwable t) {

            }
        });

    }

    private void getCity() {

        generalApiServices.getAllCities().enqueue(new Callback<Cities>() {
            @Override
            public void onResponse(Call<Cities> call, Response<Cities> response) {
                try {

                    if (response.body().getStatus().equals(1)) {
                        final List<CitiesData> citiesData = response.body().getData();
                        List<String> CityTxt = new ArrayList<String>();
                        final List<Integer> CityId = new ArrayList<Integer>();
                        CityId.add(0);
                        CityTxt.add("Choose City");

                        for (int i = 0; i < citiesData.size(); i++) {
                            CityTxt.add(citiesData.get(i).getName());
                            CityId.add(citiesData.get(i).getId());
                        }
                        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                                android.R.layout.simple_spinner_item, CityTxt);
                        restaurantCityFilter.setAdapter(adapter);
                        restaurantCityFilter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                if (i != 0) {
                                    city_id = CityId.get(i);
                                }
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> adapterView) {

                            }
                        });

                    }


                } catch (Exception e) {

                }

            }

            @Override
            public void onFailure(Call<Cities> call, Throwable t) {

            }
        });

    }

    private void FilterRestaurant() {
        Keyword = restaurantName.getText().toString();
        generalApiServices.getFilterRestaurants(Keyword, city_id).enqueue(new Callback<RestaurantsList>() {
            @Override
            public void onResponse(Call<RestaurantsList> call, Response<RestaurantsList> response) {
                if (response.body().getStatus().equals(1)) {

                    restaurantsListData.clear();
                    restaurantListAdapter.notifyDataSetChanged();

                    restaurantsListData.addAll(response.body().getData().getData());
                    restaurantListAdapter.notifyDataSetChanged();


                }
            }

            @Override
            public void onFailure(Call<RestaurantsList> call, Throwable t) {

            }
        });

    }


    @OnClick(R.id.search)
    public void onViewClicked() {
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FilterRestaurant();
            }
        });
    }
}
