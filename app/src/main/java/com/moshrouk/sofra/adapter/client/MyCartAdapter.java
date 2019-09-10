package com.moshrouk.sofra.adapter.client;


import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.moshrouk.sofra.R;
import com.moshrouk.sofra.data.local.AppDatabase;
import com.moshrouk.sofra.data.local.Item;
import com.moshrouk.sofra.helper.ImageViewExecution;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyCartAdapter extends RecyclerView.Adapter<MyCartAdapter.MyCartViewHolder> {

    public List<Item> orderItems = new ArrayList<>();
    Activity activity;
    public static Integer RestaurantId;
    public static Double total;


    private AppDatabase database;
    private int quantity;


    public MyCartAdapter(List<Item> orderItems, Activity activity) {
        this.orderItems = orderItems;
        this.activity = activity;
    }

    @NonNull
    @Override
    public MyCartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_of_cart_list, parent, false);
        database = AppDatabase.getAppDatabase(activity);
        return new MyCartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyCartViewHolder holder, final int position) {
        holder.restaurantItemName.setText(orderItems.get(position).getNameItem());
        holder.itemPrice.setText("" + orderItems.get(position).getPrice());
        holder.quantityNum.setText("" + orderItems.get(position).getQuantity());
        ImageViewExecution.LodeImage(activity, orderItems.get(position).getImageUrl(), holder.restaurantItemImage,
                R.drawable.pizzamin);

        holder.decrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quantity = Integer.parseInt(holder.quantityNum.getText().toString());
                if (quantity != 1) {
                    quantity--;
                    holder.quantityNum.setText("" + quantity);

                    database.getItemDAO().update(orderItems.get(position).getIdItems(), quantity);
                    total = (Double.valueOf(orderItems.get(position).getPrice()) * quantity);
                }

            }
        });
        holder.increase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quantity = Integer.parseInt(holder.quantityNum.getText().toString());
                quantity++;
                holder.quantityNum.setText("" + quantity);
                database.getItemDAO().update(orderItems.get(position).getIdItems(), quantity);
                total = Double.valueOf(orderItems.get(position).getPrice()) * quantity;
            }
        });
        holder.deleteItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                database.getItemDAO().delete(orderItems.get(position).getIdItems());

              orderItems.remove(position);
              orderItems.clear();
              orderItems.notifyAll();
                Toast.makeText(activity, "Successful Delete Item", Toast.LENGTH_SHORT).show();


                if (orderItems.isEmpty()){
                    database.getItemDAO().deleteAll();
                }
            }

        });

        RestaurantId = orderItems.get(position).getIdRestaurant();
    }

    public  Double getTotal() {
        Double AllTotal = 0.00;
            if (!orderItems.isEmpty()) {
                for (int i = 0; i < database.getItemDAO().getItems().size(); i++) {
                    AllTotal = AllTotal + Double.valueOf(database.getItemDAO().getItems().get(i).getPrice())
                            * (database.getItemDAO().getItems().get(i).getQuantity());
                }
                return AllTotal;
            }

        return AllTotal;
    }


    @Override
    public int getItemCount() {
        return orderItems.size();
    }

    class MyCartViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.restaurant_item_image)
        ImageView restaurantItemImage;
        @BindView(R.id.restaurant_item_name)
        TextView restaurantItemName;
        @BindView(R.id.item_price)
        TextView itemPrice;
        @BindView(R.id.delete_Item)
        FloatingActionButton deleteItem;
        @BindView(R.id.increase)
        FloatingActionButton increase;
        @BindView(R.id.quantity_num)
        TextView quantityNum;
        @BindView(R.id.decrease)
        FloatingActionButton decrease;

        View view;

        MyCartViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            view = itemLayoutView;
            ButterKnife.bind(this, view);
        }

    }

}
