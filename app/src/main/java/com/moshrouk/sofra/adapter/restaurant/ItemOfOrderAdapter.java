package com.moshrouk.sofra.adapter.restaurant;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.moshrouk.sofra.R;
import com.moshrouk.sofra.data.model.restaurant.restaurantorder.Item;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ItemOfOrderAdapter extends RecyclerView.Adapter<ItemOfOrderAdapter.ItemOfOrderViewHolder> {
    Activity activity;
    List<Item> items = new ArrayList<>();

    public ItemOfOrderAdapter(Activity activity, List<Item> items) {
        this.activity = activity;
        this.items = items;
    }

    @NonNull
    @Override
    public ItemOfOrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_of_items_list, parent, false);
        return new ItemOfOrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemOfOrderViewHolder holder, int position) {
        holder.orderName.setText(items.get(position).getName());
        holder.orderPrice.setText(""+items.get(position).getPrice());
        holder.orderDetailsName.setText(items.get(position).getPivot().getNote());
        holder.orderDetailsPrice.setText(items.get(position).getPivot().getPrice());


    }

    @Override
    public int getItemCount() {
        return items.size();

    }

    public class ItemOfOrderViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.order_name)
        TextView orderName;
        @BindView(R.id.order_price)
        TextView orderPrice;
        @BindView(R.id.order_details_name)
        TextView orderDetailsName;
        @BindView(R.id.order_details_price)
        TextView orderDetailsPrice;
        @BindView(R.id.order_detail_name)
        TextView orderDetailName;
        @BindView(R.id.order_detail_price)
        TextView orderDetailPrice;
        View view;

        public ItemOfOrderViewHolder(@NonNull View itemView) {

            super(itemView);
            view = itemView;
            ButterKnife.bind(this, view);

        }
    }
}
