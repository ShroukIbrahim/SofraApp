package com.moshrouk.sofra.adapter.restaurant;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.moshrouk.sofra.R;
import com.moshrouk.sofra.data.model.restaurant.restaurantoffers.OffersData;
import com.moshrouk.sofra.data.model.restaurant.restaurantoffers.RestaurantOffers;
import com.moshrouk.sofra.data.reset.restaurant.RestaurantApiServices;
import com.moshrouk.sofra.helper.HelperMethod;
import com.moshrouk.sofra.helper.ImageViewExecution;
import com.moshrouk.sofra.ui.fragment.Restaurant.homecycle.REditOfferFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.moshrouk.sofra.data.reset.restaurant.RetrofitRestaurant.getRestaurant;
import static com.moshrouk.sofra.helper.SharedPreferencesManger.LoadData;
import static com.moshrouk.sofra.helper.SharedPreferencesManger.USER_API_TOKEN;

public class RestaurantOfferAdapter extends RecyclerView.Adapter<RestaurantOfferAdapter.RestaurantOfferViewHolder> {


    private Activity activity;
    List<OffersData> offerData = new ArrayList<>();
    private RestaurantApiServices restaurantApiServices = getRestaurant().create(RestaurantApiServices.class);


    public RestaurantOfferAdapter(Activity activity, List<OffersData> offerData) {
        this.activity = activity;
        this.offerData = offerData;
    }

    @NonNull
    @Override
    public RestaurantOfferViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_of_restaurant_offer, parent, false);
        return new RestaurantOfferViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RestaurantOfferViewHolder holder, int position) {
        holder.offerName.setText(offerData.get(position).getDescription());
        ImageViewExecution.LodeImage(activity, offerData.get(position).getRestaurant().getPhotoUrl(), holder.restaurantImage, R.drawable.reslogo);
        int offer_id = offerData.get(position).getId();
        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              restaurantApiServices.deleteOffer(offer_id, LoadData(activity,USER_API_TOKEN))
                      .enqueue(new Callback<RestaurantOffers>() {
                          @Override
                          public void onResponse(Call<RestaurantOffers> call, Response<RestaurantOffers> response) {
                              if (response.body().getStatus().equals(1)) {
                                  Toast.makeText(activity,response.body().getMsg()+ "", Toast.LENGTH_SHORT).show();
                              }
                          }

                          @Override
                          public void onFailure(Call<RestaurantOffers> call, Throwable t) {

                          }
                      });
                
            }
        });
        holder.editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                REditOfferFragment editOfferFragment = new REditOfferFragment();
                FragmentManager manager = ((AppCompatActivity) activity).getSupportFragmentManager();
                HelperMethod.replace(editOfferFragment, manager,R.id.restaurant_home_frame,null,null);
                Bundle bundle = new Bundle();
                bundle.putInt("id", offerData.get(position).getId());
                bundle.putString("offerName", offerData.get(position).getName());
                bundle.putString("offerDes",offerData.get(position).getDescription());
                bundle.putString("offerStart",offerData.get(position).getStartingAt());
                bundle.putString("offerEnd",offerData.get(position).getEndingAt());
                bundle.putString("itemImage", offerData.get(position).getPhotoUrl());
                editOfferFragment.setArguments(bundle);

            }
        });

    }

    @Override
    public int getItemCount() {
        return offerData.size();
    }


    public class RestaurantOfferViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.offer_name)
        TextView offerName;
        @BindView(R.id.restaurant_Image)
        ImageView restaurantImage;
        @BindView(R.id.delete_button)
        ImageButton deleteButton;
        @BindView(R.id.edit_button)
        ImageButton editButton;
        View view;

        public RestaurantOfferViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;
            ButterKnife.bind(this, view);
        }
    }
}
