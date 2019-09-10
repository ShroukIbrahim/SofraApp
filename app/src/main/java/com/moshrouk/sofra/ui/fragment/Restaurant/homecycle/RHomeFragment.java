package com.moshrouk.sofra.ui.fragment.Restaurant.homecycle;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.moshrouk.sofra.R;
import com.moshrouk.sofra.adapter.restaurant.RestaurantAdapter;
import com.moshrouk.sofra.data.model.restaurant.restaurantitems.RestaurantData;
import com.moshrouk.sofra.data.model.restaurant.restaurantitems.RestaurantItems;
import com.moshrouk.sofra.data.reset.restaurant.RestaurantApiServices;
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

import static com.moshrouk.sofra.data.reset.restaurant.RetrofitRestaurant.getRestaurant;
import static com.moshrouk.sofra.helper.SharedPreferencesManger.LoadData;
import static com.moshrouk.sofra.helper.SharedPreferencesManger.USER_API_TOKEN;
import static com.moshrouk.sofra.helper.SharedPreferencesManger.USER_NAME;

/**
 * A simple {@link Fragment} subclass.
 */
public class RHomeFragment extends Fragment {


    @BindView(R.id.restaurant_name)
    TextView restaurantName;
    @BindView(R.id.home_fragment_resturant)
    RecyclerView homeFragmentRestaurant;
    @BindView(R.id.add_item)
    FloatingActionButton addItem;
    Unbinder unbinder;
    @BindView(R.id.relative)
    RelativeLayout relative;
    @BindView(R.id.swipe_layout)
    SwipeRefreshLayout swipeLayout;
    private LinearLayoutManager layoutManager;
    private List<RestaurantData> restaurantData = new ArrayList<>();
    private RestaurantApiServices restaurantApiServices = getRestaurant().create(RestaurantApiServices.class);
    private RestaurantAdapter restaurantadapter;

    public RHomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_restarunt_home, container, false);
        unbinder = ButterKnife.bind(this, view);
        getAllItems(true);
        homeFragmentRestaurant.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());
        homeFragmentRestaurant.setLayoutManager(layoutManager);
        restaurantadapter = new RestaurantAdapter(getActivity(), restaurantData);
        homeFragmentRestaurant.setItemAnimator(new DefaultItemAnimator());
        homeFragmentRestaurant.addItemDecoration(new DividerItemDecoration
                (getActivity(), DividerItemDecoration.VERTICAL));
        homeFragmentRestaurant.setAdapter(restaurantadapter);

        swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getAllItems(true);

            }
        });

        return view;

    }

    private void getAllItems(boolean refresh) {
        if (refresh | !refresh) {
            restaurantApiServices.getAllItems(LoadData(getActivity(), USER_API_TOKEN))
                    .enqueue(new Callback<RestaurantItems>() {
                        @Override
                        public void onResponse(Call<RestaurantItems> call, Response<RestaurantItems> response) {
                            if (response.body().getStatus().equals(1)) {
                                restaurantName.setText(LoadData(getActivity(), USER_NAME));
                                restaurantData.addAll(response.body().getData().getData());
                                restaurantadapter.notifyDataSetChanged();
                                swipeLayout.setRefreshing(false);


                            }

                        }

                        @Override
                        public void onFailure(Call<RestaurantItems> call, Throwable t) {

                        }
                    });

        }
    }

//    private void enableSwipeToDeleteAndUndo() {
//        SwipController swipController = new SwipController(getActivity()) {
//            @Override
//            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
//
//                final int position = viewHolder.getAdapterPosition();
//                restaurantadapter.removeItem(position);
//
//
//            }
//        };
//
//        ItemTouchHelper itemTouchhelper = new ItemTouchHelper(swipController);
//        itemTouchhelper.attachToRecyclerView(homeFragmentRestaurant);
//    }


    @OnClick(R.id.add_item)
    public void onViewClicked() {
        RAddNewItemFragment addNewItemFragment = new RAddNewItemFragment();
        HelperMethod.replace(addNewItemFragment, getFragmentManager(), R.id.restaurant_home_frame, null, null);
    }

}
