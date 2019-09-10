package com.moshrouk.sofra.adapter.restaurant;

import android.app.Activity;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.moshrouk.sofra.R;
import com.moshrouk.sofra.data.model.restaurant.restaurantorder.OrderData;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RestaurantPreviousOrder extends RecyclerView.Adapter<RestaurantPreviousOrder.RestaurantOrderViewHolder> {



    private Activity activity;
    List<OrderData> orderData = new ArrayList<>();

    public RestaurantPreviousOrder(Activity activity, List<OrderData> orderData) {
        this.activity = activity;
        this.orderData = orderData;
    }


    @NonNull
    @Override
    public RestaurantOrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_of_restaurant_previous_order, parent, false);
        return new RestaurantOrderViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull RestaurantOrderViewHolder holder, int position) {
        holder.ClientName.setText(orderData.get(position).getClient().getName());
        holder.numOfOrder.setText(""+orderData.get(position).getClient().getId());
        holder.numOfTotal.setText(orderData.get(position).getTotal());
        holder.ClientAddress.setText(orderData.get(position).getClient().getAddress());
        holder.state.setText(orderData.get(position).getState());
        if (orderData.get(position).getState().equals("accepted")) {
            holder.state.setBackgroundColor(Color.rgb(59,181,75));
        }


    }

    @Override
    public int getItemCount() {
        return orderData.size();

    }

    public class RestaurantOrderViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.client_image)
        ImageView clientImage;
        @BindView(R.id.Client_name)
        TextView ClientName;
        @BindView(R.id.num_of_order)
        TextView numOfOrder;
        @BindView(R.id.num_of_total)
        TextView numOfTotal;
        @BindView(R.id.Client_address)
        TextView ClientAddress;
        @BindView(R.id.state)
        Button state;
        View view;

        public RestaurantOrderViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;
            ButterKnife.bind(this, view);
        }

    }
}
