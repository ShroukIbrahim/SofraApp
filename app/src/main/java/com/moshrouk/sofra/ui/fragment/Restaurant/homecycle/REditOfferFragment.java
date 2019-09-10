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
import com.moshrouk.sofra.data.model.restaurant.restaurantoffers.RestaurantOffers;
import com.moshrouk.sofra.data.reset.restaurant.RestaurantApiServices;
import com.moshrouk.sofra.helper.DateModel;
import com.moshrouk.sofra.helper.HelperMethod;
import com.moshrouk.sofra.helper.ImageViewExecution;
import com.yanzhenjie.album.Action;
import com.yanzhenjie.album.AlbumFile;

import java.util.ArrayList;
import java.util.Calendar;

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
public class REditOfferFragment extends Fragment {


    @BindView(R.id.add_offer_image)
    ImageView addOfferImage;
    @BindView(R.id.offer_name)
    TextInputEditText offerName;
    @BindView(R.id.offer_dec)
    TextInputEditText offerDec;
    @BindView(R.id.from)
    Button from;
    @BindView(R.id.to)
    Button to;
    @BindView(R.id.edit_offer)
    Button addOffer;
    @BindView(R.id.price)
    TextInputEditText price;
    @BindView(R.id.offer_price)
    TextInputEditText offerPrice;
    Unbinder unbinder;
    private DateModel dateModel1;
    private DateModel dateModel2;
    final Calendar getDatenow = Calendar.getInstance();
    private int startYear;
    private int startMonth;
    private int startDay;

    private ArrayList<AlbumFile> ImagesFiles = new ArrayList<>();
    private MultipartBody.Part photoRequestBody;

    private String Description, Name, Price, OfferPrice, From, To;
    String api_token = LoadData(getActivity(), USER_API_TOKEN);
    private RequestBody requestBodyDescription;
    private RequestBody requestBodyFrom;
    private RequestBody requestBodyTo;
    private RequestBody requestBodyName;
    private RequestBody requestBodyPrice;
    private RequestBody requestBodyOfferId;
    private RequestBody requestBodyApiToken;

    private String OfferName, offerImage, offerDescription, offerStart,offerEnd;
    private int offerId;




    private RestaurantApiServices restaurantApiServices = getRestaurant().create(RestaurantApiServices.class);


    public REditOfferFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_restaurant_edit_offer, container, false);
        unbinder = ButterKnife.bind(this, view);
        startYear = getDatenow.get(Calendar.YEAR);
        startMonth = getDatenow.get(Calendar.MONTH);
        startDay = getDatenow.get(Calendar.DAY_OF_MONTH);
        dateModel1 = new DateModel(String.valueOf(startYear), String.valueOf(startMonth)
                , String.valueOf(startDay), null);
        dateModel2 = new DateModel(String.valueOf(startYear), String.valueOf(startMonth)
                , String.valueOf(startDay), null);
        getOfferData();
        Bundle bundle = getArguments();
        if (bundle != null) {
            offerId = bundle.getInt("id");
            OfferName = bundle.getString("offerName");
            offerImage = bundle.getString("itemImage");
            offerDescription = bundle.getString("offerDes");
            offerStart = bundle.getString("offerStart");
            offerEnd = bundle.getString("offerEnd");

        }

        return view;
    }

    private void getOfferData() {
        ImageViewExecution.LodeImage(getActivity(),
                offerImage,
                addOfferImage, R.drawable.pizzamin);
        offerName.setHint(OfferName);
        offerDec.setHint(offerDescription);
        from.setText(offerStart);
        to.setText(offerEnd);

    }

    private void editOffer() {
        Description = offerDec.getText().toString();
        Price = price.getText().toString();
        From = from.getText().toString();
        Name = offerName.getText().toString();
        OfferPrice = offerPrice.getText().toString();
        To = to.getText().toString();


        requestBodyDescription = HelperMethod.convertToRequestBody(Description);
        requestBodyPrice = HelperMethod.convertToRequestBody(Price);
        requestBodyFrom = HelperMethod.convertToRequestBody(From);
        requestBodyName = HelperMethod.convertToRequestBody(Name);
        requestBodyOfferId = HelperMethod.convertToRequestBody(OfferPrice);
        requestBodyTo = HelperMethod.convertToRequestBody(To);
        requestBodyApiToken = HelperMethod.convertToRequestBody(api_token);

        restaurantApiServices.updateOffer(requestBodyDescription, requestBodyPrice, requestBodyFrom
                , requestBodyName, photoRequestBody, requestBodyTo, requestBodyOfferId,requestBodyApiToken)
                .enqueue(new Callback<RestaurantOffers>() {
                    @Override
                    public void onResponse(Call<RestaurantOffers> call, Response<RestaurantOffers> response) {
                        if (response.body().getStatus().equals(1)) {
                            Toast.makeText(getActivity(), response.body().getMsg() + "", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getActivity(), response.body().getMsg() + "", Toast.LENGTH_SHORT).show();

                        }
                    }

                    @Override
                    public void onFailure(Call<RestaurantOffers> call, Throwable t) {
                        Toast.makeText(getActivity(), "Failure", Toast.LENGTH_SHORT).show();

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
                        .into(addOfferImage);
                String path = ImagesFiles.get(0).getPath();
                photoRequestBody = HelperMethod.convertTOMultipart(path, "photo");

            }
        };


        openAlbum(getActivity(), ImagesFiles, action);
    }


    @OnClick({R.id.add_offer_image, R.id.from, R.id.to, R.id.edit_offer})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.add_offer_image:
                selectedImage();
                break;
            case R.id.from:
                HelperMethod.showCalender(getActivity(), getString(R.string.from), from, dateModel1);

                break;
            case R.id.to:
                HelperMethod.showCalender(getActivity(), getString(R.string.to), to, dateModel2);

                break;
            case R.id.edit_offer:
                editOffer();
                break;
        }
    }
}
