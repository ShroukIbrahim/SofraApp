package com.moshrouk.sofra.ui.fragment.Restaurant.homecycle;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.moshrouk.sofra.R;
import com.moshrouk.sofra.adapter.client.RestaurantReviewsAdapter;
import com.moshrouk.sofra.data.model.general.restaurantreviews.RestaurantReviews;
import com.moshrouk.sofra.data.model.general.restaurantreviews.RestaurantReviewsData;
import com.moshrouk.sofra.data.reset.general.GeneralApiServices;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.moshrouk.sofra.data.reset.general.RetrofitGeneral.getGeneral;
import static com.moshrouk.sofra.helper.SharedPreferencesManger.CLIENT_API_TOKEN;
import static com.moshrouk.sofra.helper.SharedPreferencesManger.LoadData;

/**
 * A simple {@link Fragment} subclass.
 */
public class CommentsListFragment extends Fragment {


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


    public CommentsListFragment() {
        // Required empty public constructor
    }

    public CommentsListFragment(int restaurantId) {
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
        addRateLayout.setVisibility(View.GONE);
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


}
