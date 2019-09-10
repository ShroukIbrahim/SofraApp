package com.moshrouk.sofra.ui.fragment.Restaurant.homecycle;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.google.android.material.textfield.TextInputEditText;
import com.moshrouk.sofra.R;
import com.moshrouk.sofra.data.model.restaurant.restaurantitems.RestaurantItems;
import com.moshrouk.sofra.data.reset.restaurant.RestaurantApiServices;
import com.moshrouk.sofra.helper.HelperMethod;
import com.yanzhenjie.album.Action;
import com.yanzhenjie.album.AlbumFile;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.moshrouk.sofra.data.reset.restaurant.RetrofitRestaurant.getRestaurant;
import static com.moshrouk.sofra.helper.HelperMethod.openAlbum;
import static com.moshrouk.sofra.helper.SharedPreferencesManger.LoadData;
import static com.moshrouk.sofra.helper.SharedPreferencesManger.USER_API_TOKEN;

/**
 * A simple {@link Fragment} subclass.
 */
public class RAddNewItemFragment extends Fragment {


    @BindView(R.id.add_product_image)
    ImageView ProductImage;
    @BindView(R.id.product_name)
    TextInputEditText productName;
    @BindView(R.id.product_dec)
    TextInputEditText productDec;
    @BindView(R.id.product_price)
    TextInputEditText productPrice;
    @BindView(R.id.product_offer_price)
    TextInputEditText productOfferPrice;
    @BindView(R.id.product_time)
    TextInputEditText productTime;
    @BindView(R.id.add_product)
    Button addProduct;
    Unbinder unbinder;
    @BindView(R.id.Progress_Bar)
    android.widget.ProgressBar ProgressBar;

    private ArrayList<AlbumFile> ImagesFiles = new ArrayList<>();
    private MultipartBody.Part photoRequestBody;

    private RestaurantApiServices restaurantApiServices = getRestaurant().create(RestaurantApiServices.class);

    private String description, price, preparing_time, name;
    String api_token = LoadData(getActivity(), USER_API_TOKEN);
    private RequestBody requestBodyDescription;
    private RequestBody requestBodyPrice;
    private RequestBody requestBodyPreparingTime;
    private RequestBody requestBodyName;
    private RequestBody requestBodyApiToken;


    public RAddNewItemFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_new_item, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    private void addNewItem() {
        ProgressBar.setVisibility(View.VISIBLE);
        description = productDec.getText().toString();
        price = productPrice.getText().toString();
        preparing_time = productTime.getText().toString();
        name = productName.getText().toString();

        requestBodyDescription = HelperMethod.convertToRequestBody(description);
        requestBodyPrice = HelperMethod.convertToRequestBody(price);
        requestBodyPreparingTime = HelperMethod.convertToRequestBody(preparing_time);
        requestBodyName = HelperMethod.convertToRequestBody(name);
        requestBodyApiToken = HelperMethod.convertToRequestBody(api_token);

        restaurantApiServices.addNewItems(requestBodyDescription, requestBodyPrice, requestBodyPreparingTime
                , requestBodyName, photoRequestBody, requestBodyApiToken).enqueue(new Callback<RestaurantItems>() {
            @Override
            public void onResponse(Call<RestaurantItems> call, Response<RestaurantItems> response) {
                if (response.body().getStatus().equals(1)) {
                    ProgressBar.setVisibility(View.GONE);
                    Toast.makeText(getActivity(), response.body().getMsg() + "", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(getActivity(),response.body().getMsg()+ "", Toast.LENGTH_SHORT).show();
                    ProgressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<RestaurantItems> call, Throwable t) {
                Toast.makeText(getActivity(), "Failure", Toast.LENGTH_SHORT).show();
                ProgressBar.setVisibility(View.GONE);

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
                        .into(ProductImage);
                String path = ImagesFiles.get(0).getPath();
                photoRequestBody = HelperMethod.convertTOMultipart(path, "photo");

            }
        };

        openAlbum(getActivity(), ImagesFiles, action);
    }

    @OnClick({R.id.add_product_image, R.id.add_product})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.add_product_image:
                selectedImage();
                break;
            case R.id.add_product:
                addNewItem();
                break;
        }
    }
}
