package com.moshrouk.sofra.ui.fragment.Restaurant.homecycle;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.moshrouk.sofra.R;
import com.moshrouk.sofra.data.model.restaurant.commissions.Commissions;
import com.moshrouk.sofra.data.reset.restaurant.RestaurantApiServices;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.moshrouk.sofra.data.reset.restaurant.RetrofitRestaurant.getRestaurant;
import static com.moshrouk.sofra.helper.SharedPreferencesManger.LoadData;
import static com.moshrouk.sofra.helper.SharedPreferencesManger.USER_API_TOKEN;

/**
 * A simple {@link Fragment} subclass.
 */
public class RCommissionsFragment extends Fragment {


    @BindView(R.id.restaurant_sales)
    TextView restaurantSales;
    @BindView(R.id.commission_app)
    TextView commissionApp;
    @BindView(R.id.payments)
    TextView payments;
    @BindView(R.id.residual)
    TextView residual;
    Unbinder unbinder;
    RestaurantApiServices restaurantApiServices = getRestaurant().create(RestaurantApiServices.class);

    public RCommissionsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_restaurant_commissions, container, false);
        unbinder = ButterKnife.bind(this,view);
        getCommission();
        return view;
    }

    private void getCommission() {
        restaurantApiServices.getCommissions(LoadData(getActivity(),USER_API_TOKEN))
                .enqueue(new Callback<Commissions>() {
                    @Override
                    public void onResponse(Call<Commissions> call, Response<Commissions> response) {
                        if (response.body().getStatus().equals(1)) {
                         restaurantSales.setText(response.body().getData().getCommissions());
                         commissionApp.setText(response.body().getData().getCommission());
                         payments.setText(response.body().getData().getPayments());
                         residual.setText(response.body().getData().getTotal());
                        }
                    }

                    @Override
                    public void onFailure(Call<Commissions> call, Throwable t) {

                    }
                });
    }


}
