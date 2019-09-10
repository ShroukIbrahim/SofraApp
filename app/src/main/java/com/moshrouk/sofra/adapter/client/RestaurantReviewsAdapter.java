package com.moshrouk.sofra.adapter.client;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.moshrouk.sofra.R;
import com.moshrouk.sofra.data.model.general.restaurantreviews.RestaurantReviewsData;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RestaurantReviewsAdapter extends RecyclerView.Adapter<RestaurantReviewsAdapter.RestaurantReviewsViewHolder> {

    private Activity activity;
    List<RestaurantReviewsData> restaurantReviewsData = new ArrayList<>();

    public RestaurantReviewsAdapter(Activity activity, List<RestaurantReviewsData> restaurantReviewsData) {
        this.activity = activity;
        this.restaurantReviewsData = restaurantReviewsData;
    }

    @NonNull
    @Override
    public RestaurantReviewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_of_comments, parent, false);
        return new RestaurantReviewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RestaurantReviewsViewHolder holder, int position) {
        holder.clientName.setText(restaurantReviewsData.get(position).getClient().getName());
        holder.clientComment.setText(restaurantReviewsData.get(position).getComment());
    }

    @Override
    public int getItemCount() {
        return restaurantReviewsData.size();
    }

    public class RestaurantReviewsViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.client_name)
        TextView clientName;
        @BindView(R.id.client_comment)
        TextView clientComment;
        View view;

        public RestaurantReviewsViewHolder(@NonNull View itemView) {

            super(itemView);
            view = itemView;
            ButterKnife.bind(this, view);
        }
    }
}
