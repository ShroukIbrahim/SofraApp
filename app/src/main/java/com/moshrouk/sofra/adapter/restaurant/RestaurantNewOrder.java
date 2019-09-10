package com.moshrouk.sofra.adapter.restaurant;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.moshrouk.sofra.R;
import com.moshrouk.sofra.data.model.restaurant.restaurantorder.Order;
import com.moshrouk.sofra.data.model.restaurant.restaurantorder.OrderData;
import com.moshrouk.sofra.data.reset.restaurant.RestaurantApiServices;
import com.moshrouk.sofra.helper.HelperMethod;
import com.moshrouk.sofra.ui.fragment.Restaurant.homecycle.ROrderDetailsFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.moshrouk.sofra.data.reset.restaurant.RetrofitRestaurant.getRestaurant;
import static com.moshrouk.sofra.helper.HelperMethod.makePhoneCall;
import static com.moshrouk.sofra.helper.SharedPreferencesManger.LoadData;
import static com.moshrouk.sofra.helper.SharedPreferencesManger.USER_API_TOKEN;

public class RestaurantNewOrder extends RecyclerView.Adapter<RestaurantNewOrder.RestaurantOrderViewHolder> {

    private static final int REQUEST_CALL = 1;
    private Activity activity;
    List<OrderData> orderData = new ArrayList<>();
    private RestaurantApiServices restaurantApiServices = getRestaurant().create(RestaurantApiServices.class);
    private Integer order_id;
    private String phone;


    public RestaurantNewOrder(Activity activity, List<OrderData> orderData) {
        this.activity = activity;
        this.orderData = orderData;
    }


    @NonNull
    @Override
    public RestaurantOrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_of_restaurant_new_order, parent, false);
        return new RestaurantOrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RestaurantOrderViewHolder holder, int position) {
        holder.clientName.setText(orderData.get(position).getClient().getName());
        holder.numOfOrder.setText("" + orderData.get(position).getClient().getId());
        holder.numOfTotal.setText(orderData.get(position).getTotal());
        holder.addressOfClient.setText(orderData.get(position).getClient().getAddress());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ROrderDetailsFragment rOrderDetailsFragment = new ROrderDetailsFragment();
                FragmentManager manager = ((AppCompatActivity) activity).getSupportFragmentManager();
                HelperMethod.replace(rOrderDetailsFragment, manager, R.id.restaurant_home_frame, null, null);

                Bundle bundle = new Bundle();
                bundle.putInt("id", order_id);
                rOrderDetailsFragment.setArguments(bundle);

            }
        });
        order_id = orderData.get(position).getId();
        phone = orderData.get(position).getClient().getPhone();

        holder.call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ContextCompat.checkSelfPermission(activity, Manifest.permission.CALL_PHONE) !=
                        PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CALL);
                } else {
                    makePhoneCall(activity, phone);
                }

            }
        });
        holder.accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                restaurantApiServices.acceptOrder(LoadData(activity, USER_API_TOKEN), order_id)
                        .enqueue(new Callback<Order>() {
                            @Override
                            public void onResponse(Call<Order> call, Response<Order> response) {
                                if (response.body().getStatus().equals(1)) {
                                    Toast.makeText(activity, response.body().getMsg() + "", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<Order> call, Throwable t) {

                            }
                        });

            }
        });


        holder.reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                restaurantApiServices.rejectOrder(LoadData(activity, USER_API_TOKEN), order_id).enqueue(new Callback<Order>() {
                    @Override
                    public void onResponse(Call<Order> call, Response<Order> response) {
                        if (response.body().getStatus().equals(1)) {
                            Toast.makeText(activity, response.body().getMsg() + "", Toast.LENGTH_SHORT).show();
                        } else
                            Toast.makeText(activity, response.body().getMsg() + "", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<Order> call, Throwable t) {

                    }
                });
            }
        });

    }

    @Override
    public int getItemCount() {
        return orderData.size();

    }


    public class RestaurantOrderViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.client_image)
        ImageView clientImage;
        @BindView(R.id.name_of_client)
        TextView clientName;
        @BindView(R.id.num_of_order)
        TextView numOfOrder;
        @BindView(R.id.num_of_total)
        TextView numOfTotal;
        @BindView(R.id.address_of_client)
        TextView addressOfClient;
        @BindView(R.id.call)
        Button call;
        @BindView(R.id.accept)
        Button accept;
        @BindView(R.id.reject)
        Button reject;
        View view;

        public RestaurantOrderViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;
            ButterKnife.bind(this, view);
        }

    }
}
