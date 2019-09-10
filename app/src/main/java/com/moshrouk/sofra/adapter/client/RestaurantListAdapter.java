package com.moshrouk.sofra.adapter.client;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.moshrouk.sofra.R;
import com.moshrouk.sofra.data.model.general.restaurants_list.RestaurantsListData;
import com.moshrouk.sofra.helper.HelperMethod;
import com.moshrouk.sofra.ui.fragment.Client.homecycle.restaurant.RestaurantDataFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.moshrouk.sofra.helper.ImageViewExecution.LodeImage;

public class RestaurantListAdapter extends RecyclerView.Adapter<RestaurantListAdapter.RestaurantListViewHolder> {



    private Activity activity;
    List<RestaurantsListData> restaurantsListData = new ArrayList<>();
    public static int Rid;

    public RestaurantListAdapter(Activity activity, List<RestaurantsListData> restaurantsListData) {
        this.activity = activity;
        this.restaurantsListData = restaurantsListData;
    }

    @NonNull
    @Override
    public RestaurantListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_of_restaurantslist, parent, false);
        return new RestaurantListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RestaurantListViewHolder holder, final int position) {
        holder.restaurantName.setText(restaurantsListData.get(position).getName());
        holder.restaurantRate.setRating(restaurantsListData.get(position).getRate());
        holder.minimumChargerTxt.setText(restaurantsListData.get(position).getMinimumCharger());
        holder.deliveryCostTxt.setText(restaurantsListData.get(position).getDeliveryCost());
        holder.restaurantAvailability.setText(restaurantsListData.get(position).getAvailability());
        LodeImage(activity, restaurantsListData.get(position).getPhotoUrl(),
                holder.restaurantImage, R.drawable.pizzamin);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RestaurantDataFragment restaurantDataFragment = new RestaurantDataFragment();
                FragmentManager manager = ((AppCompatActivity) activity).getSupportFragmentManager();
                HelperMethod.replace(restaurantDataFragment, manager, R.id.client_home_frame, null, null);
                    Rid =restaurantsListData.get(position).getId();
                Bundle bundle = new Bundle();
                bundle.putInt("id", restaurantsListData.get(position).getId());
                bundle.putString("restaurantName", restaurantsListData.get(position).getName());
                bundle.putString("restaurantImage", restaurantsListData.get(position).getPhotoUrl());
                restaurantDataFragment.setArguments(bundle);

            }
        });

    }

    @Override
    public int getItemCount() {
        return restaurantsListData.size();
    }

    public class RestaurantListViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.restaurant_name)
        TextView restaurantName;
        @BindView(R.id.restaurant_av)
        ImageView restaurantAv;
        @BindView(R.id.restaurant_availability)
        TextView restaurantAvailability;
        @BindView(R.id.restaurant_rate)
        RatingBar restaurantRate;
        @BindView(R.id.minimum_charger_txt)
        TextView minimumChargerTxt;
        @BindView(R.id.delivery_cost_txt)
        TextView deliveryCostTxt;
        @BindView(R.id.restaurant_image)
        ImageView restaurantImage;
        View view;

        public RestaurantListViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;
            ButterKnife.bind(this, view);
        }
    }
}
