package com.moshrouk.sofra.adapter.client;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.moshrouk.sofra.R;
import com.moshrouk.sofra.data.model.client.clientorders.ClientOrderData;
import com.moshrouk.sofra.helper.ImageViewExecution;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class ClientPreviousOrderAdapter extends RecyclerView.Adapter<ClientPreviousOrderAdapter.ClientPreviousOrderViewHolder> {

    private Activity activity;
    List<ClientOrderData> clientOrderData = new ArrayList<>();

    public ClientPreviousOrderAdapter(Activity activity, List<ClientOrderData> clientOrderData) {
        this.activity = activity;
        this.clientOrderData = clientOrderData;
    }

    @NonNull
    @Override
    public ClientPreviousOrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_of_client_previous_order, parent, false);
        return new ClientPreviousOrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ClientPreviousOrderViewHolder holder, int position) {
        holder.restaurantName.setText(clientOrderData.get(position).getRestaurant().getName());
        holder.numOfOrder.setText(""+clientOrderData.get(position).getId());
        holder.numOfTotal.setText(clientOrderData.get(position).getTotal());

        ImageViewExecution.LodeImage(activity, clientOrderData.get(position).getRestaurant().getPhotoUrl(), holder.restaurantImage, R.drawable.reslogo);


    }

    @Override
    public int getItemCount() {
        return clientOrderData.size();
    }

    public class ClientPreviousOrderViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.restaurant_image)
        CircleImageView restaurantImage;
        @BindView(R.id.restaurant_name)
        TextView restaurantName;
        @BindView(R.id.num_of_order)
        TextView numOfOrder;
        @BindView(R.id.num_of_total)
        TextView numOfTotal;
        View view;

        public ClientPreviousOrderViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;
            ButterKnife.bind(this, view);
        }
    }
}
