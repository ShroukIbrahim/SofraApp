package com.moshrouk.sofra.adapter.client;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.moshrouk.sofra.R;
import com.moshrouk.sofra.data.model.general.restaurant_items.ItemsData;
import com.moshrouk.sofra.helper.HelperMethod;
import com.moshrouk.sofra.helper.ImageViewExecution;
import com.moshrouk.sofra.ui.fragment.Client.homecycle.restaurant.RestaurantItemDataFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RestaurantItemListAdapter extends RecyclerView.Adapter<RestaurantItemListAdapter.RestaurantItemListViewHolder> {


    private Activity activity;
    List<ItemsData> itemsData = new ArrayList<>();

    public RestaurantItemListAdapter(Activity activity, List<ItemsData> itemsData) {
        this.activity = activity;
        this.itemsData = itemsData;
    }

    @NonNull
    @Override
    public RestaurantItemListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_of_restaurant_items, parent, false);
        return new RestaurantItemListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RestaurantItemListViewHolder holder, final int position) {
        holder.restaurantItemName.setText(itemsData.get(position).getName());
        holder.itemDescription.setText(itemsData.get(position).getDescription());
        holder.priceOfItem.setText(itemsData.get(position).getPrice());
        ImageViewExecution.LodeImage(activity, itemsData.get(position).getPhotoUrl(), holder.restaurantItemImage, R.drawable.pizzamin);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RestaurantItemDataFragment restaurantItemDataFragment = new RestaurantItemDataFragment();
                FragmentManager manager = ((AppCompatActivity) activity).getSupportFragmentManager();
                HelperMethod.replace(restaurantItemDataFragment, manager, R.id.client_home_frame, null, null);

                Bundle bundle = new Bundle();
                bundle.putInt("id", itemsData.get(position).getId());
                //bundle.putString("restaurantId",itemsData.get(position).getRestaurantId());
                bundle.putString("restaurantName", itemsData.get(position).getName());
                bundle.putString("restaurantItemDescription", itemsData.get(position).getDescription());
                bundle.putString("restaurantItemPrice", itemsData.get(position).getPrice());
                bundle.putString("PreparingTime", itemsData.get(position).getPreparingTime());
                bundle.putString("restaurantImage", itemsData.get(position).getPhotoUrl());
                restaurantItemDataFragment.setArguments(bundle);

            }
        });

    }

    @Override
    public int getItemCount() {
        return itemsData.size();
    }

    public class RestaurantItemListViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.restaurant_item_image)
        ImageView restaurantItemImage;
        @BindView(R.id.restaurant_item_name)
        TextView restaurantItemName;
        @BindView(R.id.item_description)
        TextView itemDescription;
        @BindView(R.id.price_of_item)
        TextView priceOfItem;
        View view;

        public RestaurantItemListViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;
            ButterKnife.bind(this, view);
        }
    }
}
