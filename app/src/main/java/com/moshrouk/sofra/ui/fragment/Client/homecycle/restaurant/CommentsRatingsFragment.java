package com.moshrouk.sofra.ui.fragment.Client.homecycle.restaurant;


import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;
import com.moshrouk.sofra.R;
import com.moshrouk.sofra.adapter.client.RestaurantReviewsAdapter;
import com.moshrouk.sofra.data.model.client.addReview.AddReview;
import com.moshrouk.sofra.data.model.general.restaurantreviews.RestaurantReviews;
import com.moshrouk.sofra.data.model.general.restaurantreviews.RestaurantReviewsData;
import com.moshrouk.sofra.data.reset.client.ClientApiServices;
import com.moshrouk.sofra.data.reset.general.GeneralApiServices;
import com.moshrouk.sofra.helper.RatingBarEmoji;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.moshrouk.sofra.data.reset.client.RetrofitClient.getClient;
import static com.moshrouk.sofra.data.reset.general.RetrofitGeneral.getGeneral;
import static com.moshrouk.sofra.helper.SharedPreferencesManger.CLIENT_API_TOKEN;
import static com.moshrouk.sofra.helper.SharedPreferencesManger.LoadData;

/**
 * A simple {@link Fragment} subclass.
 */
public class CommentsRatingsFragment extends Fragment {


    @BindView(R.id.add_rate)
    Button addRate;
    @BindView(R.id.commentslist)
    RecyclerView commentslist;
    Unbinder unbinder;
    @BindView(R.id.add_rate_layout)
    RelativeLayout addRateLayout;


    private int restaurantId;

    private RestaurantReviewsAdapter restaurantReviewsAdapter;
    private LinearLayoutManager linearLayoutManager;
    private List<RestaurantReviewsData> restaurantReviewsData = new ArrayList<>();
    private GeneralApiServices generalApiServices = getGeneral().create(GeneralApiServices.class);
    private ClientApiServices clientApiServices = getClient().create(ClientApiServices.class);
    private float userValue;
    private String AddNot;


    public CommentsRatingsFragment() {
        // Required empty public constructor
    }

    public CommentsRatingsFragment(int restaurantId) {
        this.restaurantId = restaurantId;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_comments_ratings, container, false);
        unbinder = ButterKnife.bind(this, view);
        commentslist.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(getActivity());
        commentslist.setLayoutManager(linearLayoutManager);
        restaurantReviewsAdapter = new RestaurantReviewsAdapter(getActivity(), restaurantReviewsData);
        commentslist.setAdapter(restaurantReviewsAdapter);
        addRateLayout.setVisibility(View.VISIBLE);
        getRestaurantReviews();
        return view;
    }

    private void getRestaurantReviews() {
        generalApiServices.getRestaurantComments(LoadData(getActivity(), CLIENT_API_TOKEN), restaurantId)
                .enqueue(new Callback<RestaurantReviews>() {
                    @Override
                    public void onResponse(Call<RestaurantReviews> call, Response<RestaurantReviews> response) {
                        if (response.body().getStatus().equals(1)) {
                            restaurantReviewsData.addAll(response.body().getData().getData());
                            restaurantReviewsAdapter.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onFailure(Call<RestaurantReviews> call, Throwable t) {

                    }
                });
    }

    @OnClick(R.id.add_rate)
    public void onViewClicked() {
        addRate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShowRatingDialog();

            }
        });
    }

    //************Rating Button *********************
    public void ShowRatingDialog() {
        Dialog RatingBar = new Dialog(getActivity(), R.style.FullHeightDialog);
        RatingBar.setContentView(R.layout.custom_add_comment);
        RatingBar.setCancelable(true);
        RatingBarEmoji ratingBarEmoji = (RatingBarEmoji) RatingBar.findViewById(R.id.add_rate);
        ratingBarEmoji.setOnRatingBarChangeListener(
                new android.widget.RatingBar.OnRatingBarChangeListener() {
                    @Override
                    public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                        userValue = rating;
                        ratingBar.setRating(rating);


                    }
                });

        ratingBarEmoji.setRating(1);

        TextInputEditText add_not = (TextInputEditText) RatingBar.findViewById(R.id.add_not);
        AddNot = add_not.getText().toString();
        Button updateButton = (Button) RatingBar.findViewById(R.id.add);
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addRate();
                RatingBar.dismiss();
            }
        });
        //now that the dialog is set up, it's time to show it    
        RatingBar.show();
    }

    private void addRate() {
        clientApiServices.AddReview(userValue, AddNot, restaurantId,
                LoadData(getActivity(), CLIENT_API_TOKEN)).enqueue(new Callback<AddReview>() {
            @Override
            public void onResponse(Call<AddReview> call, Response<AddReview> response) {
                try {
                    if (response.body().getStatus().equals(1)) {

                        Toast.makeText(getActivity(), response.body().getMsg(), Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(getActivity(), response.body().getMsg(), Toast.LENGTH_SHORT).show();

                    }
                } catch (Exception e) {
                    e.getMessage();
                }

            }

            @Override
            public void onFailure(Call<AddReview> call, Throwable t) {

            }
        });

    }


}
