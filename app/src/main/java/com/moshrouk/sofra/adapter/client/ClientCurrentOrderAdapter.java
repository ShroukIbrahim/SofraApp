package com.moshrouk.sofra.adapter.client;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.moshrouk.sofra.R;
import com.moshrouk.sofra.data.model.client.clientorders.ClientOrderData;
import com.moshrouk.sofra.data.model.client.clientorders.ClientOrders;
import com.moshrouk.sofra.data.reset.client.ClientApiServices;
import com.moshrouk.sofra.helper.ImageViewExecution;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.moshrouk.sofra.data.reset.client.RetrofitClient.getClient;
import static com.moshrouk.sofra.helper.SharedPreferencesManger.LoadData;
import static com.moshrouk.sofra.helper.SharedPreferencesManger.USER_API_TOKEN;

public class ClientCurrentOrderAdapter extends RecyclerView.Adapter<ClientCurrentOrderAdapter.ClientOrderViewHolder> {

    private Activity activity;
    private List<ClientOrderData> clientOrderData = new ArrayList<>();
    private ClientApiServices clientApiServices = getClient().create(ClientApiServices.class);
    private Integer order_id;

    public ClientCurrentOrderAdapter(Activity activity, List<ClientOrderData> clientOrderData) {
        this.activity = activity;
        this.clientOrderData = clientOrderData;
    }

    @NonNull
    @Override
    public ClientOrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_of_client_current_order, parent, false);
        return new ClientOrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ClientOrderViewHolder holder, int position) {
        holder.restaurantName.setText(clientOrderData.get(position).getRestaurant().getName());
        holder.numOfOrder.setText(""+clientOrderData.get(position).getRestaurant().getId());
        holder.numOfTotal.setText(clientOrderData.get(position).getTotal());
        ImageViewExecution.LodeImage(activity,clientOrderData.get(position).getRestaurant().getPhotoUrl(),holder.restaurantImage,R.drawable.reslogo);
        order_id = clientOrderData.get(position).getId();

        holder.accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                clientApiServices.confirmOrder(order_id,LoadData(activity, USER_API_TOKEN))
                        .enqueue(new Callback<ClientOrders>() {
                            @Override
                            public void onResponse(Call<ClientOrders> call, Response<ClientOrders> response) {
                                if (response.body().getStatus().equals(1)) {
                                    Toast.makeText(activity, response.body().getMsg() + "", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<ClientOrders> call, Throwable t) {

                            }
                        });

            }
        });


        holder.reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clientApiServices.declineOrder( order_id,LoadData(activity, USER_API_TOKEN)).enqueue(new Callback<ClientOrders>() {
                    @Override
                    public void onResponse(Call<ClientOrders> call, Response<ClientOrders> response) {
                        if (response.body().getStatus().equals(1)) {
                            Toast.makeText(activity, response.body().getMsg() + "", Toast.LENGTH_SHORT).show();
                        } else
                            Toast.makeText(activity, response.body().getMsg() + "", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<ClientOrders> call, Throwable t) {

                    }
                });
            }
        });



    }

    @Override
    public int getItemCount() {
        return clientOrderData.size();
    }


    public class ClientOrderViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.restaurant_image)
        CircleImageView restaurantImage;
        @BindView(R.id.restaurant_name)
        TextView restaurantName;
        @BindView(R.id.num_of_order)
        TextView numOfOrder;
        @BindView(R.id.num_of_total)
        TextView numOfTotal;
        @BindView(R.id.accept)
        Button accept;
        @BindView(R.id.reject)
        Button reject;
        @BindView(R.id.button_layout)
        LinearLayout buttonLayout;
        View view;

        public ClientOrderViewHolder(@NonNull View itemView) {
            super(itemView);
            view=itemView;
            ButterKnife.bind(this, view);
        }
    }
}
