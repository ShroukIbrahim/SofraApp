package com.moshrouk.sofra.ui.fragment.Restaurant.homecycle;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.google.android.material.textfield.TextInputEditText;
import com.moshrouk.sofra.R;
import com.moshrouk.sofra.data.model.general.cities.Cities;
import com.moshrouk.sofra.data.model.general.cities.CitiesData;
import com.moshrouk.sofra.data.model.restaurant.restaurantprofile.Profile;
import com.moshrouk.sofra.data.model.restaurant.restaurantprofile.User;
import com.moshrouk.sofra.data.reset.general.GeneralApiServices;
import com.moshrouk.sofra.data.reset.restaurant.RestaurantApiServices;
import com.moshrouk.sofra.helper.HelperMethod;
import com.moshrouk.sofra.helper.ImageViewExecution;
import com.yanzhenjie.album.Action;
import com.yanzhenjie.album.AlbumFile;

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
import static com.moshrouk.sofra.data.reset.restaurant.RetrofitRestaurant.getRestaurant;
import static com.moshrouk.sofra.helper.HelperMethod.openAlbum;
import static com.moshrouk.sofra.helper.SharedPreferencesManger.LoadData;
import static com.moshrouk.sofra.helper.SharedPreferencesManger.USER_API_TOKEN;
import static com.moshrouk.sofra.helper.SharedPreferencesManger.USER_PASSWORD;

/**
 * A simple {@link Fragment} subclass.
 */
public class RProfileFragment extends Fragment {


    @BindView(R.id.fragment_profile_restaurant_image)
    ImageView RestaurantImage;
    @BindView(R.id.fragment_profile_restaurant_name)
    TextInputEditText RestaurantName;
    @BindView(R.id.fragment_profile_restaurant_email)
    TextInputEditText RestaurantEmail;
    @BindView(R.id.fragment_profile_restaurant_phone)
    TextInputEditText RestaurantPhone;
    @BindView(R.id.fragment_profile_restaurant_city)
    Spinner RestaurantCity;
    @BindView(R.id.fragment_profile_restaurant_password)
    TextInputEditText RestaurantPassword;
    @BindView(R.id.fragment_profile_restaurant_cpassword)
    TextInputEditText RestaurantCpassword;
    @BindView(R.id.Progress_Bar)
    android.widget.ProgressBar ProgressBar;
    @BindView(R.id.fragment_profile_restaurant_next)
    Button RestaurantNext;
    Unbinder unbinder;
    @BindView(R.id.fragment_profile_restaurant_region)
    Spinner RestaurantRegion;
    private GeneralApiServices generalApiServices = getGeneral().create(GeneralApiServices.class);
    private RestaurantApiServices restaurantApiServices = getRestaurant().create(RestaurantApiServices.class);

    private ArrayList<AlbumFile> ImagesFiles = new ArrayList<>();
    private User user;



    private int city_id;
    private int region_id;
    private String city_txt;
    private String path;



    public RProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_restaurant_profile, container, false);
        unbinder = ButterKnife.bind(this,view);
        getProfileData();
        return view;
    }

    public void selectedImage() {

        Action<ArrayList<AlbumFile>> action = new Action<ArrayList<AlbumFile>>() {
            @Override
            public void onAction(@NonNull ArrayList<AlbumFile> result) {
                ImagesFiles.clear();
                ImagesFiles.addAll(result);
                Glide.with(getActivity()).load(ImagesFiles.get(0).getPath())
                        .into(RestaurantImage);
                path = ImagesFiles.get(0).getPath();

            }
        };

        openAlbum(getActivity(), ImagesFiles, action);
    }

    private void getCity(final int id) {

        generalApiServices.getAllCities().enqueue(new Callback<Cities>() {
            @Override
            public void onResponse(Call<Cities> call, Response<Cities> response) {
                try {

                    if (response.body().getStatus().equals(1)) {
                        final List<CitiesData> citiesData = response.body().getData();
                        final List<String> CityTxt = new ArrayList<String>();
                        final List<Integer> CityId = new ArrayList<Integer>();
                        CityId.add(id);
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

    private void getRegions(final int id) {
        generalApiServices.getregons(id).enqueue(new Callback<Cities>() {
            @Override
            public void onResponse(Call<Cities> call, Response<Cities> response) {
                try {

                    if (response.body().getStatus().equals(1)) {
                        final List<CitiesData> citiesData = response.body().getData();
                        List<String> RegionTxt = new ArrayList<String>();
                        final List<Integer> RegionId = new ArrayList<Integer>();
                        RegionId.add(id);
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


    private void getProfileData() {
        restaurantApiServices.getUserData(LoadData(getActivity(), USER_API_TOKEN))
                .enqueue(new Callback<Profile>() {
                    @Override
                    public void onResponse(Call<Profile> call, Response<Profile> response) {
                        try {
                            if (response.body().getStatus()==1) {
                                ImageViewExecution.LodeImage(getActivity(),
                                        response.body().getData().getUser().getPhotoUrl(),
                                        RestaurantImage, R.drawable.add_user);
                                RestaurantEmail.setHint(response.body().getData().getUser().getEmail());
                                RestaurantName.setHint(response.body().getData().getUser().getName());
                                RestaurantPhone.setHint(response.body().getData().getUser().getPhone());
                                RestaurantPassword.setHint(LoadData(getActivity(), USER_PASSWORD));
                                RestaurantCpassword.setHint(LoadData(getActivity(), USER_PASSWORD));
                                getCity(response.body().getData().getUser().getRegion().getCity().getId());
                                getRegions(response.body().getData().getUser().getRegion().getId());

                               String RestaurantMinimumCharger = response.body().getData().getUser().getMinimumCharger();
                               String RestaurantDeliveryCost = response.body().getData().getUser().getDeliveryCost();
                               user = response.body().getData().getUser();

                            }
                            else
                                Toast.makeText(getActivity(),response.body().getMsg()+ "", Toast.LENGTH_SHORT).show();
                        }catch (Exception e){
                            Toast.makeText(getActivity(), "exception error", Toast.LENGTH_SHORT).show();

                        }

                    }

                    @Override
                    public void onFailure(Call<Profile> call, Throwable t) {

                    }
                });

    }


    @OnClick({R.id.fragment_profile_restaurant_next, R.id.fragment_profile_restaurant_image})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.fragment_profile_restaurant_image:
               selectedImage();

                break;
            case R.id.fragment_profile_restaurant_next:

                        ProgressBar.setVisibility(View.VISIBLE);
                        //String Image = path.getBytes().toString();
                        String Name = RestaurantName.getText().toString();
                        String Email = RestaurantEmail.getText().toString();
                        String Password = RestaurantPassword.getText().toString();
                        String CPassword = RestaurantCpassword.getText().toString();
                        String Phone = RestaurantPhone.getText().toString();



                        Bundle bundle = new Bundle();

                        bundle.putString("path",path);
                        bundle.putString("name", Name);
                        bundle.putString("email", Email);
                        bundle.putString("password", Password);
                        bundle.putString("cpassword", CPassword);
                        bundle.putString("phone", Phone);
                        bundle.putString("city_name", city_txt);
                        bundle.putInt("region_id", region_id);

                        RFinalProfileFragment RFinalProfileFragment = new RFinalProfileFragment(user);
                        RFinalProfileFragment.setArguments(bundle);
                        if (getFragmentManager() != null) {
                            HelperMethod.replace(RFinalProfileFragment, getActivity().getSupportFragmentManager(), R.id.restaurant_home_frame, null, null);
                            ProgressBar.setVisibility(View.GONE);
                        } else {
                            Toast.makeText(getActivity(), "please Enter All Data", Toast.LENGTH_SHORT).show();

                        }

        }


    }
}
