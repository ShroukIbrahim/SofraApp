package com.moshrouk.sofra.adapter.general;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.moshrouk.sofra.R;
import com.moshrouk.sofra.data.model.general.offers.OfferData;
import com.moshrouk.sofra.helper.ImageViewExecution;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class OfferAdapter extends RecyclerView.Adapter<OfferAdapter.OfferAdapterViewHolder> {


    private Activity activity;
    List<OfferData> offerData = new ArrayList<>();

    public OfferAdapter(Activity activity, List<OfferData> offerData) {
        this.activity = activity;
        this.offerData = offerData;
    }

    @NonNull
    @Override
    public OfferAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_of_client_offer, parent, false);
        return new OfferAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OfferAdapterViewHolder holder, int position) {
        holder.offerName.setText(offerData.get(position).getDescription());
        ImageViewExecution.LodeImage(activity, offerData.get(position).getRestaurant().getPhotoUrl(), holder.restaurantImage, R.drawable.reslogo);


    }

    @Override
    public int getItemCount() {
        return offerData.size();
    }

    @OnClick(R.id.get_offer)
    public void onViewClicked() {
    }

    public class OfferAdapterViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.offer_name)
        TextView offerName;
        @BindView(R.id.get_offer)
        Button getOffer;
        @BindView(R.id.restaurant_Image)
        ImageView restaurantImage;
        View view;

        public OfferAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;
            ButterKnife.bind(this, view);
        }
    }
}
