package com.moshrouk.sofra.adapter.restaurant;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.moshrouk.sofra.R;
import com.moshrouk.sofra.data.model.restaurant.restaurantorder.Order;
import com.moshrouk.sofra.data.model.restaurant.restaurantorder.OrderData;
import com.moshrouk.sofra.data.reset.restaurant.RestaurantApiServices;

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

public class RestaurantCurrentOrderAdapter extends RecyclerView.Adapter<RestaurantCurrentOrderAdapter.RestaurantOrderViewHolder> {


    private static final int REQUEST_CALL =1 ;
    private Activity activity;
    List<OrderData> orderData = new ArrayList<>();
    private RestaurantApiServices restaurantApiServices = getRestaurant().create(RestaurantApiServices.class);
    private Integer order_id;
    private String phone;

    public RestaurantCurrentOrderAdapter(Activity activity, List<OrderData> orderData) {
        this.activity = activity;
        this.orderData = orderData;
    }


    @NonNull
    @Override
    public RestaurantOrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_of_restaurant_current_order, parent, false);
        return new RestaurantOrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RestaurantOrderViewHolder holder, int position) {
        holder.nameOfClient.setText(orderData.get(position).getClient().getName());
        holder.numOfOrder.setText(""+orderData.get(position).getClient().getId());
        holder.numOfTotal.setText(orderData.get(position).getTotal());
        holder.addressOfClient.setText(orderData.get(position).getClient().getAddress());
        holder.call.setText(orderData.get(position).getClient().getPhone());
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

                restaurantApiServices.confirmOrder( order_id,LoadData(activity, USER_API_TOKEN))
                        .enqueue(new Callback<Order>() {
                            @Override
                            public void onResponse(Call<Order> call, Response<Order> response) {
                                if (response.body().getStatus().equals(1)) {
                                    Toast.makeText(activity,response.body().getMsg()+ "", Toast.LENGTH_SHORT).show();
                                }
                                else
                                    Toast.makeText(activity,response.body().getMsg()+ "", Toast.LENGTH_SHORT).show();
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
        @BindView(R.id.name_of_client)
        TextView nameOfClient;
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
        View view;

        public RestaurantOrderViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;
            ButterKnife.bind(this, view);
        }

    }
}
