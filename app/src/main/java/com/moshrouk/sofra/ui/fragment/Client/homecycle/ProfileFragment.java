package com.moshrouk.sofra.ui.fragment.Client.homecycle;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
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
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.moshrouk.sofra.data.reset.general.RetrofitGeneral.getGeneral;
import static com.moshrouk.sofra.data.reset.restaurant.RetrofitRestaurant.getRestaurant;
import static com.moshrouk.sofra.helper.HelperMethod.openAlbum;
import static com.moshrouk.sofra.helper.SharedPreferencesManger.CLIENT_API_TOKEN;
import static com.moshrouk.sofra.helper.SharedPreferencesManger.LoadData;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {


    @BindView(R.id.fragment_profile_client_image)
    CircleImageView ClientImage;
    @BindView(R.id.fragment_profile_client_name)
    TextInputEditText ClientName;
    @BindView(R.id.fragment_profile_client_email)
    TextInputEditText ClientEmail;
    @BindView(R.id.fragment_profile_client_phone)
    TextInputEditText ClientPhone;
    @BindView(R.id.fragment_profile_client_city)
    Spinner ClientCity;
    @BindView(R.id.fragment_profile_client_region)
    Spinner ClientRegion;
    @BindView(R.id.Progress_Bar)
    android.widget.ProgressBar ProgressBar;
    @BindView(R.id.fragment_profile_client_edit)
    Button ClientEditData;
    Unbinder unbinder;
    private GeneralApiServices generalApiServices=getGeneral().create(GeneralApiServices.class);
    private RestaurantApiServices restaurantApiServices= getRestaurant().create(RestaurantApiServices.class);
    private int city_id;
    private int region_id;
    private String city_txt;

    private ArrayList<AlbumFile> ImagesFiles = new ArrayList<>();
    private MultipartBody.Part photoRequestBody;

    private MultipartBody.Part image;
    private String name;
    private String email;
    private String phoneNum;
    private RequestBody requestBodyName,requestBodyEmail,requestBodyPhone,
            requestBodyRegionId;




    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_client_profile, container, false);
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
                        .into(ClientImage);
                String path = ImagesFiles.get(0).getPath();
                photoRequestBody = HelperMethod.convertTOMultipart(path, "photo");


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
                        ClientCity.setAdapter(adapter);
                        ClientCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
                        ClientRegion.setAdapter(adapter);

                        ClientRegion.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
        restaurantApiServices.getUserData(LoadData(getActivity(), CLIENT_API_TOKEN))
                .enqueue(new Callback<Profile>() {
                    @Override
                    public void onResponse(Call<Profile> call, Response<Profile> response) {
                        try {
                            if (response.body().getStatus()==1) {
                                ImageViewExecution.LodeImage(getActivity(),
                                        response.body().getData().getUser().getPhotoUrl(),
                                        ClientImage, R.drawable.add_user);
                                ClientEmail.setHint(response.body().getData().getUser().getEmail());
                                ClientName.setHint(response.body().getData().getUser().getName());
                                ClientPhone.setHint(response.body().getData().getUser().getPhone());

                                getCity(response.body().getData().getUser().getRegion().getCity().getId());
                                getRegions(response.body().getData().getUser().getRegion().getId());

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

    private void editProfileData() {
    }

    @OnClick({R.id.fragment_profile_client_image, R.id.fragment_profile_client_edit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.fragment_profile_client_image:
                selectedImage();
                break;
            case R.id.fragment_profile_client_edit:
                editProfileData();
                break;
        }
    }


}
