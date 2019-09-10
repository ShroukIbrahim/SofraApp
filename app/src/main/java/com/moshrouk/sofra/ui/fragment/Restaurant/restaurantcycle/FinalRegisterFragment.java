package com.moshrouk.sofra.ui.fragment.Restaurant.restaurantcycle;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.google.android.material.textfield.TextInputEditText;
import com.moshrouk.sofra.R;
import com.moshrouk.sofra.adapter.restaurant.SpinnerAdapter;
import com.moshrouk.sofra.data.model.general.categories.Categories;
import com.moshrouk.sofra.data.model.general.categories.CategoriesData;
import com.moshrouk.sofra.data.model.restaurant.restaurantregister.RestaurantRegister;
import com.moshrouk.sofra.data.reset.general.GeneralApiServices;
import com.moshrouk.sofra.data.reset.restaurant.RestaurantApiServices;
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

import static com.moshrouk.sofra.data.reset.general.RetrofitGeneral.getGeneral;
import static com.moshrouk.sofra.data.reset.restaurant.RetrofitRestaurant.getRestaurant;
import static com.moshrouk.sofra.helper.HelperMethod.openAlbum;

/**
 * A simple {@link Fragment} subclass.
 */
public class FinalRegisterFragment extends Fragment {


    @BindView(R.id.fragment_register_restaurant_categories)
    Spinner RestaurantCategories;
    @BindView(R.id.fragment_register_restaurant_minimum_charger)
    TextInputEditText RestaurantMinimumCharger;
    @BindView(R.id.fragment_register_restaurant_delivery_cost)
    TextInputEditText RestaurantDeliveryCost;
    @BindView(R.id.fragment_register_restaurant_phone1)
    TextInputEditText RestaurantPhone1;
    @BindView(R.id.fragment_register_restaurant_whatsapp)
    TextInputEditText RestaurantWhatsapp;
    @BindView(R.id.fragment_register_restaurant_store)
    ImageButton fragmentRegisterRestaurantStore;
    @BindView(R.id.fragment_register_restaurant)
    Button fragmentRegisterRestaurant;
    Unbinder unbinder;
    @BindView(R.id.Progress_Bar)
    android.widget.ProgressBar ProgressBar;

    private GeneralApiServices generalApiServices = getGeneral().create(GeneralApiServices.class);
    private RestaurantApiServices restaurantApiServices = getRestaurant().create(RestaurantApiServices.class);

    private int region_id;

    private String name, email, password, cPassword, phoneNum,
            whatsUp, delivery_cost, minimum_charge, availability = "Open";
    private RequestBody requestBodyName;
    private RequestBody requestBodyEmail;
    private RequestBody requestBodyPassword;
    private RequestBody requestBodyCPassword;
    private RequestBody requestBodyPhone;
    private RequestBody requestBodyWhatsUp;
    private RequestBody requestBodyDeliveryCost;
    private RequestBody requestBodyRegionId;
    private RequestBody requestBodyMinimumCharge;
    private RequestBody requestBodyAvailability;

    private ArrayList<AlbumFile> ImagesFiles = new ArrayList<>();
    private MultipartBody.Part photoRequestBody;


    public FinalRegisterFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_restarunt_register1, container, false);
        unbinder = ButterKnife.bind(this, view);
        getCategories();
        RestaurantPhone1.setText(phoneNum);
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            name = bundle.getString("name");
            email = bundle.getString("email");
            password = bundle.getString("password");
            cPassword = bundle.getString("cpassword");
            phoneNum = bundle.getString("phone");
            region_id = bundle.getInt("region_id");

        }
        return view;
    }


    private void getCategories() {

        generalApiServices.getAllCategories().enqueue(new Callback<Categories>() {
            @Override
            public void onResponse(Call<Categories> call, Response<Categories> response) {
                try {

                    if (response.body().getStatus().equals(1)) {
                        final List<CategoriesData> categoryData = response.body().getData();
                        ArrayList<SpinnerAdapter.StateSpinner> stateSpinnerList = new ArrayList<>();
                        SpinnerAdapter.StateSpinner stateSpinner = new SpinnerAdapter.StateSpinner();
                        stateSpinner.setTitle("Categories");
                        stateSpinner.setSelected(false);
                        stateSpinnerList.add(stateSpinner);
                        for (int i = 0; i < categoryData.size(); i++) {
                            stateSpinner = new SpinnerAdapter.StateSpinner();
                            stateSpinner.setTitle(categoryData.get(i).getName());
                            stateSpinner.setSelected(false);
                            stateSpinnerList.add(stateSpinner);


                        }
                        SpinnerAdapter spinnerAdapter = new SpinnerAdapter(getActivity(), 0, stateSpinnerList);
                        RestaurantCategories.setAdapter(spinnerAdapter);

                    }

                } catch (Exception e) {

                }

            }

            @Override
            public void onFailure(Call<Categories> call, Throwable t) {

            }
        });

    }

    private void RestaurantRegister() {
        minimum_charge = RestaurantMinimumCharger.getText().toString();
        delivery_cost = RestaurantDeliveryCost.getText().toString();
        whatsUp = RestaurantWhatsapp.getText().toString();

        requestBodyName = HelperMethod.convertToRequestBody(name);
        requestBodyEmail = HelperMethod.convertToRequestBody(email);
        requestBodyPassword = HelperMethod.convertToRequestBody(password);
        requestBodyCPassword = HelperMethod.convertToRequestBody(cPassword);
        requestBodyPhone = HelperMethod.convertToRequestBody(phoneNum);
        requestBodyWhatsUp = HelperMethod.convertToRequestBody(whatsUp);
        requestBodyDeliveryCost = HelperMethod.convertToRequestBody(delivery_cost);
        requestBodyMinimumCharge = HelperMethod.convertToRequestBody(minimum_charge);
        requestBodyAvailability = HelperMethod.convertToRequestBody(availability);
        requestBodyRegionId = HelperMethod.convertToRequestBody(String.valueOf(region_id));
        List<String> categoryList = SpinnerAdapter.selectedCategory;
        List<RequestBody> category = new ArrayList<>();
        for (int i = 0; i < categoryList.size(); i++) {
            category.add(HelperMethod.convertToRequestBody(categoryList.get(i)));
        }
        ProgressBar.setVisibility(View.VISIBLE);
        restaurantApiServices.restaurantRegister(requestBodyName, requestBodyEmail, requestBodyPassword,
                requestBodyCPassword, requestBodyPhone, requestBodyWhatsUp,
                requestBodyRegionId, category, requestBodyDeliveryCost, requestBodyMinimumCharge, photoRequestBody).enqueue(new Callback<RestaurantRegister>() {
            @Override
            public void onResponse(Call<RestaurantRegister> call, Response<RestaurantRegister> response) {
                if (response.body().getStatus()==1) {
                    ProgressBar.setVisibility(View.GONE);
                    Toast.makeText(getActivity(), response.body().getMsg() + "", Toast.LENGTH_SHORT).show();

                }
                else
                    Toast.makeText(getActivity(), response.body().getMsg() + "", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<RestaurantRegister> call, Throwable t) {
                Toast.makeText(getActivity(),"Failure", Toast.LENGTH_SHORT).show();

            }
        });


    }

    public void selectedImage() {

        Action<ArrayList<AlbumFile>> action = new Action<ArrayList<AlbumFile>>() {
            @Override
            public void onAction(@NonNull ArrayList<AlbumFile> result) {
                ImagesFiles.clear();
                ImagesFiles.addAll(result);
                Glide.with(getActivity()).load(ImagesFiles.get(0).getPath())
                        .into(fragmentRegisterRestaurantStore);
                String path = ImagesFiles.get(0).getPath();
                photoRequestBody = HelperMethod.convertTOMultipart(path, "photo");

            }
        };

        openAlbum(getActivity(), ImagesFiles, action);
    }


    @OnClick({R.id.fragment_register_restaurant_store, R.id.fragment_register_restaurant})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.fragment_register_restaurant_store:
                fragmentRegisterRestaurantStore.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        selectedImage();
                    }
                });

                break;
            case R.id.fragment_register_restaurant:
                fragmentRegisterRestaurant.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        RestaurantRegister();
                    }
                });

                break;
        }
    }

}
