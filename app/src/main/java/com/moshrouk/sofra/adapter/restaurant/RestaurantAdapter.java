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
import com.moshrouk.sofra.data.model.restaurant.restaurantitems.RestaurantData;
import com.moshrouk.sofra.data.model.restaurant.restaurantitems.RestaurantItems;
import com.moshrouk.sofra.data.reset.restaurant.RestaurantApiServices;
import com.moshrouk.sofra.helper.HelperMethod;
import com.moshrouk.sofra.helper.ImageViewExecution;
import com.moshrouk.sofra.ui.fragment.Restaurant.homecycle.REditItemFragment;

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

public class RestaurantAdapter extends RecyclerView.Adapter<RestaurantAdapter.RestaurantViewHolder> {


    private Activity activity;
    List<RestaurantData> restaurantData = new ArrayList<>();
    private RestaurantApiServices restaurantApiServices = getRestaurant().create(RestaurantApiServices.class);


    public RestaurantAdapter(Activity activity, List<RestaurantData> restaurantData) {
        this.activity = activity;
        this.restaurantData = restaurantData;
    }

    @NonNull
    @Override
    public RestaurantViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_of_restaurant, parent, false);
        return new RestaurantViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RestaurantViewHolder holder, int position) {
        holder.ItemName.setText(restaurantData.get(position).getName());
        holder.ItemDec.setText(restaurantData.get(position).getDescription());
        holder.ItemPrice.setText(restaurantData.get(position).getPrice());
        ImageViewExecution.LodeImage(activity, restaurantData.get(position).getPhotoUrl(), holder.ItemImage, R.drawable.pizzamin);

        int item_id = restaurantData.get(position).getId();
        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                restaurantApiServices.deleteItem(item_id,LoadData(activity,USER_API_TOKEN))
                        .enqueue(new Callback<RestaurantItems>() {
                            @Override
                            public void onResponse(Call<RestaurantItems> call, Response<RestaurantItems> response) {
                                if (response.body().getStatus().equals(1)) {

                                    Toast.makeText(activity, response.body().getMsg()+"...", Toast.LENGTH_SHORT).show();

                                }
                            }

                            @Override
                            public void onFailure(Call<RestaurantItems> call, Throwable t) {

                            }
                        });


            }
        });
        holder.editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                REditItemFragment editItemFragment = new REditItemFragment();
                FragmentManager manager = ((AppCompatActivity) activity).getSupportFragmentManager();
                HelperMethod.replace(editItemFragment, manager,R.id.restaurant_home_frame,null,null);
                Bundle bundle = new Bundle();
                bundle.putInt("id", restaurantData.get(position).getId());
                bundle.putString("itemName", restaurantData.get(position).getName());
                bundle.putString("itemDes",restaurantData.get(position).getDescription());
                bundle.putString("itemPrice",restaurantData.get(position).getPrice());
                bundle.putString("itemOfferPrice",restaurantData.get(position).getPrice());
                bundle.putString("itemPrepTime",restaurantData.get(position).getPreparingTime());
                bundle.putString("itemImage", restaurantData.get(position).getPhotoUrl());
                editItemFragment.setArguments(bundle);

            }
        });


    }

    @Override
    public int getItemCount() {
        return restaurantData.size();
    }



//    public void removeItem(int position) {
//        restaurantData.remove(position);
//        notifyItemRemoved(position);
//    }

    public class RestaurantViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.category_item_image)
        ImageView ItemImage;
        @BindView(R.id.category_item_name)
        TextView ItemName;
        @BindView(R.id.category_item_dec)
        TextView ItemDec;
        @BindView(R.id.category_item_price)
        TextView ItemPrice;
        @BindView(R.id.delete_button)
        ImageButton deleteButton;
        @BindView(R.id.edit_button)
        ImageButton editButton;
        View view;

        public RestaurantViewHolder(@NonNull View itemView) {

            super(itemView);
            view = itemView;
            ButterKnife.bind(this, view);
        }
    }
}
