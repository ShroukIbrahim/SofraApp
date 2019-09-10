package com.moshrouk.sofra.ui.fragment.Client.clientcycle;


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
import com.moshrouk.sofra.data.model.client.clientregister.ClientRegister;
import com.moshrouk.sofra.data.model.general.cities.Cities;
import com.moshrouk.sofra.data.model.general.cities.CitiesData;
import com.moshrouk.sofra.data.reset.client.ClientApiServices;
import com.moshrouk.sofra.data.reset.general.GeneralApiServices;
import com.moshrouk.sofra.helper.HelperMethod;
import com.yanzhenjie.album.Action;
import com.yanzhenjie.album.AlbumFile;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.moshrouk.sofra.data.reset.client.RetrofitClient.getClient;
import static com.moshrouk.sofra.data.reset.general.RetrofitGeneral.getGeneral;
import static com.moshrouk.sofra.helper.HelperMethod.openAlbum;

/**
 * A simple {@link Fragment} subclass.
 */
public class RegisterFragment extends Fragment {


    @BindView(R.id.client_image)
    ImageView ClientImage;
    @BindView(R.id.fragment_register_client_name)
    TextInputEditText ClientName;
    @BindView(R.id.fragment_register_client_email)
    TextInputEditText ClientEmail;
    @BindView(R.id.fragment_register_client_phone)
    TextInputEditText ClientPhone;
    @BindView(R.id.fragment_register_client_city)
    Spinner ClientCity;
    @BindView(R.id.fragment_register_client_region)
    Spinner ClientRegion;
    @BindView(R.id.fragment_register_client_password)
    TextInputEditText ClientPassword;
    @BindView(R.id.fragment_register_client_cpassword)
    TextInputEditText ClientCpassword;
    @BindView(R.id.fragment_register_client_register)
    Button ClientRegister;
    Unbinder unbinder;
    @BindView(R.id.Progress_Bar)
    android.widget.ProgressBar ProgressBar;
    private Integer city_id;
    private Integer region_id;
    private String city_txt;

    ClientApiServices clientApiServices = getClient().create(ClientApiServices.class);
    GeneralApiServices generalApiServices = getGeneral().create(GeneralApiServices.class);

    private String name, email, password, cPassword, phoneNum;
    private RequestBody requestBodyName;
    private RequestBody requestBodyEmail;
    private RequestBody requestBodyPassword;
    private RequestBody requestBodyCPassword;
    private RequestBody requestBodyPhone;
    private RequestBody requestBodyRegionId;
    private RequestBody requestBodyCityText;


    private ArrayList<AlbumFile> ImagesFiles = new ArrayList<>();
    private MultipartBody.Part photoRequestBody;


    public RegisterFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_client_register, container, false);
        unbinder = ButterKnife.bind(this,view);
        getCity();
        return view;
    }

    private void selectedImage() {

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


    private void clientRegister() {
        ProgressBar.setVisibility(View.VISIBLE);
        name = ClientName.getText().toString();
        email = ClientEmail.getText().toString();
        password = ClientPassword.getText().toString();
        cPassword = ClientCpassword.getText().toString();
        phoneNum = ClientPhone.getText().toString();

        requestBodyName = HelperMethod.convertToRequestBody(name);
        requestBodyEmail = HelperMethod.convertToRequestBody(email);
        requestBodyPassword = HelperMethod.convertToRequestBody(password);
        requestBodyCPassword = HelperMethod.convertToRequestBody(cPassword);
        requestBodyPhone = HelperMethod.convertToRequestBody(phoneNum);
        requestBodyRegionId = HelperMethod.convertToRequestBody(String.valueOf(region_id));
        requestBodyCityText = HelperMethod.convertToRequestBody(city_txt);


        clientApiServices.clientRegister(requestBodyName, requestBodyEmail, requestBodyPassword,
                requestBodyCPassword, requestBodyPhone, requestBodyCityText, requestBodyRegionId, photoRequestBody)
                .enqueue(new Callback<ClientRegister>() {
                    @Override
                    public void onResponse(Call<ClientRegister> call, Response<ClientRegister> response) {
                        try {
                            if (response.body().getStatus().equals(1)) {
                                ProgressBar.setVisibility(View.GONE);
                                Toast.makeText(getActivity(), response.body().getMsg() + "", Toast.LENGTH_SHORT).show();
                            } else
                                ProgressBar.setVisibility(View.GONE);
                                Toast.makeText(getActivity(), response.body().getMsg() + "", Toast.LENGTH_SHORT).show();

                        } catch (Exception e) {
                            ProgressBar.setVisibility(View.GONE);
                            Toast.makeText(getActivity(), "error ex", Toast.LENGTH_SHORT).show();
                        }


                    }

                    @Override
                    public void onFailure(Call<ClientRegister> call, Throwable t) {

                    }
                });
    }




    @OnClick({R.id.client_image, R.id.fragment_register_client_register})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.client_image:
              selectedImage();

                break;
            case R.id.fragment_register_client_register:
               clientRegister();

                break;
        }
    }
}


