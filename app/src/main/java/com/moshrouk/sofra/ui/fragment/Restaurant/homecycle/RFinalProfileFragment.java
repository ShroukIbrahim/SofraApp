package com.moshrouk.sofra.ui.fragment.Restaurant.homecycle;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputEditText;
import com.moshrouk.sofra.R;
import com.moshrouk.sofra.adapter.restaurant.SpinnerAdapter;
import com.moshrouk.sofra.data.model.general.categories.Categories;
import com.moshrouk.sofra.data.model.general.categories.CategoriesData;
import com.moshrouk.sofra.data.model.restaurant.restaurantprofile.User;
import com.moshrouk.sofra.data.model.restaurant.restaurantregister.RestaurantRegister;
import com.moshrouk.sofra.data.reset.general.GeneralApiServices;
import com.moshrouk.sofra.data.reset.restaurant.RestaurantApiServices;
import com.moshrouk.sofra.helper.HelperMethod;

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

/**
 * A simple {@link Fragment} subclass.
 */
public class RFinalProfileFragment extends Fragment {


    @BindView(R.id.fragment_profile_restaurant_categories)
    Spinner RestaurantCategories;
    @BindView(R.id.fragment_profile_restaurant_minimum_charger)
    TextInputEditText RestaurantMinimumCharger;
    @BindView(R.id.fragment_profile_restaurant_delivery_cost)
    TextInputEditText RestaurantDeliveryCost;
    @BindView(R.id.fragment_profile_restaurant_status)
    Switch RestaurantStatus;
    @BindView(R.id.fragment_profile_restaurant_phone1)
    TextInputEditText RestaurantPhone1;
    @BindView(R.id.fragment_profile_restaurant_whatsapp)
    TextInputEditText RestaurantWhatsapp;
    @BindView(R.id.fragment_profile_restaurant_edit)
    Button EditRestaurant;
    @BindView(R.id.Progress_Bar)
    android.widget.ProgressBar ProgressBar;
    Unbinder unbinder;
    private GeneralApiServices generalApiServices = getGeneral().create(GeneralApiServices.class);
    private RestaurantApiServices restaurantApiServices = getRestaurant().create(RestaurantApiServices.class);

    private int region_id;

    private MultipartBody.Part image;
    private String name;
    private String email;
    private String password;
    private String cPassword;
    private String phoneNum;
    private String whatsUp;
    private String delivery_cost;
    private String minimum_charge;
    private String availability = "Open";
    private RequestBody requestBodyName,requestBodyEmail,requestBodyPassword,requestBodyCPassword,requestBodyPhone
            ,requestBodyWhatsUp,requestBodyDeliveryCost,requestBodyRegionId,requestBodyMinimumCharge,requestBodyAvailability;
    private String path;
    private MultipartBody.Part photoRequestBody;

    private User user;



    public RFinalProfileFragment() {
        // Required empty public constructor
    }

    public RFinalProfileFragment(User user) {
        this.user = user;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_restaurant_finall_profile, container, false);
        //getRestaurantData();
        unbinder = ButterKnife.bind(this, view);
        RestaurantMinimumCharger.setHint(user.getMinimumCharger());
        RestaurantDeliveryCost.setHint(user.getDeliveryCost());
        RestaurantPhone1.setText(user.getPhone());
        RestaurantWhatsapp.setHint(user.getWhatsapp());
        RestaurantStatus.setText(user.getAvailability());


        Bundle bundle = this.getArguments();
        if (bundle != null) {

            path = bundle.getString("path");
            name = bundle.getString("name");
            email = bundle.getString("email");
            password = bundle.getString("password");
            cPassword = bundle.getString("cpassword");
            phoneNum = bundle.getString("phone");
            region_id = bundle.getInt("region_id");

        }

        return view;
    }
    private void getCategories(final List<Integer> ids) {

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
                            for (int j = 0; j < ids.size(); j++) {
                                stateSpinner.setSelected(true);

                            }
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
//    private void getRestaurantData(){
//        restaurantApiServices.getUserData(LoadData(getActivity(), USER_API_TOKEN))
//                .enqueue(new Callback<Profile>() {
//                    @Override
//                    public void onResponse(Call<Profile> call, Response<Profile> response) {
//                        if (response.body().getStatus().equals(1)) {
//                            RestaurantMinimumCharger.setText(response.body().getData().getUser().getMinimumCharger());
//                            RestaurantDeliveryCost.setText(response.body().getData().getUser().getDeliveryCost());
//                            RestaurantPhone1.setText(response.body().getData().getUser().getPhone());
//                            RestaurantWhatsapp.setText(response.body().getData().getUser().getWhatsapp());
//                            RestaurantStatus.setText(response.body().getData().getUser().getAvailability());
//
//
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(Call<Profile> call, Throwable t) {
//
//                    }
//                });
//
//
//    }


    private void editProfileData() {
        minimum_charge = RestaurantMinimumCharger.getText().toString();
        delivery_cost = RestaurantDeliveryCost.getText().toString();
        whatsUp = RestaurantWhatsapp.getText().toString();

        photoRequestBody = HelperMethod.convertTOMultipart(path, "photo");
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

        restaurantApiServices.restaurantRegister(requestBodyName, requestBodyEmail, requestBodyPassword,
                requestBodyCPassword, requestBodyPhone, requestBodyWhatsUp,
                requestBodyRegionId, category, requestBodyDeliveryCost, requestBodyMinimumCharge,photoRequestBody).enqueue(new Callback<RestaurantRegister>() {
            @Override
            public void onResponse(Call<RestaurantRegister> call, Response<RestaurantRegister> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(getActivity(), response.body().getMsg() + "", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<RestaurantRegister> call, Throwable t) {

            }
        });


    }
    @OnClick(R.id.fragment_profile_restaurant_edit)
    public void onViewClicked() {
        editProfileData();
    }


}
