package com.moshrouk.sofra.ui.fragment.Restaurant.restaurantcycle;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputEditText;
import com.moshrouk.sofra.R;
import com.moshrouk.sofra.data.model.general.cities.Cities;
import com.moshrouk.sofra.data.model.general.cities.CitiesData;
import com.moshrouk.sofra.data.reset.general.GeneralApiServices;
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

import static com.moshrouk.sofra.data.reset.general.RetrofitGeneral.getGeneral;

/**
 * A simple {@link Fragment} subclass.
 */
public class RegisterFragment extends Fragment {

    @BindView(R.id.fragment_register_restaurant_name)
    TextInputEditText RestaurantName;
    @BindView(R.id.fragment_register_restaurant_email)
    TextInputEditText RestaurantEmail;
    @BindView(R.id.fragment_register_restaurant_phone)
    TextInputEditText RestaurantPhone;
    @BindView(R.id.fragment_register_restaurant_city)
    Spinner RestaurantCity;
    @BindView(R.id.fragment_register_restaurant_region)
    Spinner RestaurantRegion;
    @BindView(R.id.fragment_register_restaurant_password)
    TextInputEditText RestaurantPassword;
    @BindView(R.id.fragment_register_restaurant_cpassword)
    TextInputEditText RestaurantCpassword;
    @BindView(R.id.fragment_register_restaurant_next)
    Button RestaurantNext;
    Unbinder unbinder;
    @BindView(R.id.Progress_Bar)
    android.widget.ProgressBar ProgressBar;
    private GeneralApiServices generalApiServices = getGeneral().create(GeneralApiServices.class);
    private int city_id;
    private int region_id;
    private String city_txt;


    public RegisterFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_restaurant_register, container, false);
        unbinder = ButterKnife.bind(this, view);
        getCity();
        getRegions(city_id);
        return view;
    }


    private void getCity() {

        generalApiServices.getAllCities().enqueue(new Callback<Cities>() {
            @Override
            public void onResponse(Call<Cities> call, Response<Cities> response) {
                try {

                    if (response.body().getStatus().equals(1)) {
                        final List<CitiesData> citiesData = response.body().getData();
                        final List<String> CityTxt = new ArrayList<String>();
                        final List<Integer> CityId = new ArrayList<Integer>();
                        CityId.add(0);
                        CityTxt.add("City");

                        for (int i = 0; i < citiesData.size(); i++) {
                            CityTxt.add(citiesData.get(i).getName());
                            CityId.add(citiesData.get(i).getId());
                        }
                        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                                android.R.layout.simple_spinner_item, CityTxt);
                        RestaurantCity.setAdapter(adapter);
                        RestaurantCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                if (i != 0) {
                                    city_id = CityId.get(i);
                                    city_txt = CityTxt.get(i);
                                    getRegions(city_id);
                                    Toast.makeText(getActivity(), city_id + "", Toast.LENGTH_SHORT).show();
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

    private void getRegions(int id) {
        generalApiServices.getregons(id).enqueue(new Callback<Cities>() {
            @Override
            public void onResponse(Call<Cities> call, Response<Cities> response) {
                try {

                    if (response.body().getStatus().equals(1)) {
                        final List<CitiesData> citiesData = response.body().getData();
                        List<String> RegionTxt = new ArrayList<String>();
                        final List<Integer> RegionId = new ArrayList<Integer>();
                        RegionId.add(0);
                        RegionTxt.add("Region");
                        for (int i = 0; i < citiesData.size(); i++) {
                            RegionTxt.add(citiesData.get(i).getName());
                            RegionId.add(citiesData.get(i).getId());
                        }
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                                android.R.layout.simple_spinner_item, RegionTxt);
                        RestaurantRegion.setAdapter(adapter);

                        RestaurantRegion.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                if (i != 0) {

                                    region_id = RegionId.get(i);
                                    Toast.makeText(getActivity(), region_id + "", Toast.LENGTH_SHORT).show();

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

    @OnClick(R.id.fragment_register_restaurant_next)
    public void onViewClicked() {
        RestaurantNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ProgressBar.setVisibility(View.VISIBLE);
                String Name = RestaurantName.getText().toString();
                String Email = RestaurantEmail.getText().toString();
                String Password = RestaurantPassword.getText().toString();
                String CPassword = RestaurantCpassword.getText().toString();
                String Phone = RestaurantPhone.getText().toString();

                Bundle bundle = new Bundle();
                bundle.putString("name", Name);
                bundle.putString("email", Email);
                bundle.putString("password", Password);
                bundle.putString("cpassword", CPassword);
                bundle.putString("phone", Phone);
                bundle.putString("city_name", city_txt);
                bundle.putInt("region_id", region_id);
                FinalRegisterFragment finalRegisterFragment = new FinalRegisterFragment();
                finalRegisterFragment.setArguments(bundle);
                if (getFragmentManager() != null) {
                    ProgressBar.setVisibility(View.GONE);
                    HelperMethod.replace(finalRegisterFragment, getActivity().getSupportFragmentManager(), R.id.resturant_cycle, null, null);

                } else {
                    Toast.makeText(getActivity(), "please Enter All Data", Toast.LENGTH_SHORT).show();

                }

            }
        });
    }
}
 